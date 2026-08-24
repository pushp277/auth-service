package org.sageDelta.auth_service.services.userService;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.clients.BasicClient;
import org.sageDelta.auth_service.dao.LoginUserDetail;
import org.sageDelta.auth_service.exceptions.clients.ClientNotFoundException;
import org.sageDelta.auth_service.exceptions.clients.ClientUrlNotFoundException;
import org.sageDelta.auth_service.model.*;
import org.sageDelta.auth_service.services.token.JwtTokenService;
import org.sageDelta.auth_service.utils.Utils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final List<BasicClient> clients;
    private final JwtTokenService jwtTokenService;

    record AccessAndRefreshToken(String accessToken, String refreshToken){}

    public URI authorize(
                String clientId,
                String redirectUri,
                String session,
                String scope
        ) {
            BasicClient client = clients.stream()
                    .filter(elm ->
                            elm.clientId().equals(clientId))
                    .findFirst()
                    .orElseThrow(()-> new ClientNotFoundException("Client not found"));

            String findRedirectUrl = client.redirectUrls()
                    .stream()
                    .filter(elm -> elm.equals(redirectUri))
                    .findFirst()
                    .orElseThrow(()-> new ClientUrlNotFoundException("Client url not found"));


            String authCode = Utils.getAuthCode();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication ==  null)
                throw new IllegalStateException("Authentication can't be null");

        LoginUserDetail userDetail = (LoginUserDetail) authentication.getPrincipal();
            redisTemplate.opsForValue().set(
                    client.clientId()+"::"+authCode+"::" + client.clientSecret(), // clientId::authCode::clientSecret
                    userDetail==null ? LoginUserDetail.builder().build() : userDetail,
                    120,
                    TimeUnit.SECONDS
            );

            return UriComponentsBuilder
                    .fromUriString(findRedirectUrl)
                    .queryParam("code", authCode)
                    .queryParam("scope", scope)
                    .build()
                    .toUri();
    }

    public CreateTokenResponse createToken(CreateTokenRequest request){
        BasicClient client = clients.stream()
                .filter(elm ->
                        elm.clientId().equals(request.getClientId()))
                .findFirst()
                .orElseThrow(()-> new ClientNotFoundException("Client not found"));


        String key = client.clientId() + "::" + request.getAuthorizationCode() + "::" + client.clientSecret();

        Object cachedObject = redisTemplate.opsForValue().get(key);

        if (cachedObject == null) {
            throw new ClientNotFoundException("auth token is wrong or expired");
        }

        if (!(cachedObject instanceof LoginUserDetail)) {
            throw new IllegalStateException("Stored session data is not of type LoginUserDetail");
        }

        LoginUserDetail loginUserDetail = (LoginUserDetail) cachedObject;
        AccessAndRefreshToken accessAndRefreshToken = createRefreshAndAccessToken(request.getClientId(), loginUserDetail);

        CreateTokenResponse response = new CreateTokenResponse();

        response.setAccessToken(accessAndRefreshToken.accessToken());
        response.setRefreshToken(accessAndRefreshToken.refreshToken());
        return response;
    }


    public RefreshTokenResponse refreshToken(RefreshTokenRequest request){
        RefreshTokenResponse response = new RefreshTokenResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authentication cannot be null or unauthenticated");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof LoginUserDetail)) {
            throw new IllegalStateException("Principal is not of type LoginUserDetail, found: " +
                    (principal != null ? principal.getClass().getName() : "null"));
        }

        LoginUserDetail loginUserDetail = (LoginUserDetail) principal;


        String refreshKey = "refreshToken:"+loginUserDetail.getUsername()+":"+request.getRefreshToken();
        boolean optionalKey = Boolean.TRUE.equals(redisTemplate.hasKey(refreshKey));

        if(!optionalKey)
            throw new ClientNotFoundException("refreshToken is wrong or expired");


        AccessAndRefreshToken accessAndRefreshToken = createRefreshAndAccessToken(request.getClientId(), loginUserDetail);
        response.setAccessToken(accessAndRefreshToken.accessToken());
        response.setRefreshToken(accessAndRefreshToken.refreshToken());

        return response;
    }


    private AccessAndRefreshToken createRefreshAndAccessToken(String clientId,  LoginUserDetail loginUserDetail){
        String jti = Utils.generateSalt();
        JWTClaims claims = new JWTClaims();
        claims.setAud(clientId);
        claims.setSub(loginUserDetail.getUsername());
        claims.setJti(jti);
        claims.setRole("USER");
        claims.setIss("SageDelta");


        String refreshToken = Utils.generateRefreshToken();

        String refreshKey = "refreshToken:"+loginUserDetail.getUsername()+":"+refreshToken;
        redisTemplate.opsForValue().set(
                refreshKey,
                loginUserDetail,
                1,
                TimeUnit.DAYS);

        String accessToken = jwtTokenService.getJwt(claims, loginUserDetail);

        return new AccessAndRefreshToken(accessToken, refreshToken);
    }

}
