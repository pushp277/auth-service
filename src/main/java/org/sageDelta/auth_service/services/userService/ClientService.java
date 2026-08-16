package org.sageDelta.auth_service.services.userService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.clients.BasicClient;
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

            redisTemplate.opsForValue().set(
                    client.clientId()+"::"+authCode+"::" + client.clientSecret(), // clientId::authCode::clientSecret
                    new Object(),
                    60,
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


        String key = client.clientId() + "::"+ request.getAuthorizationCode() + "::" + client.clientSecret();
        boolean optionalKey = Boolean.TRUE.equals(redisTemplate.hasKey(key));

        if(!optionalKey)
            throw new ClientNotFoundException("auth token is wrong or expired");



        AccessAndRefreshToken accessAndRefreshToken = createRefreshAndAccessToken(request.getClientId());

        CreateTokenResponse response = new CreateTokenResponse();

        response.setAccessToken(accessAndRefreshToken.accessToken());
        response.setRefreshToken(accessAndRefreshToken.refreshToken());
        return response;
    }


    public RefreshTokenResponse refreshToken(RefreshTokenRequest request){
        RefreshTokenResponse response = new RefreshTokenResponse();
        AccessAndRefreshToken accessAndRefreshToken = createRefreshAndAccessToken(request.getClientId());
        response.setAccessToken(accessAndRefreshToken.accessToken());
        response.setRefreshToken(accessAndRefreshToken.refreshToken());

        return response;
    }


    private AccessAndRefreshToken createRefreshAndAccessToken(String clientId){
        String jti = Utils.generateSalt();
        JWTClaims claims = new JWTClaims();
        claims.setAud(clientId);
        claims.setJti(jti);
        claims.setRole("USER");
        claims.setIss("SageDelta");

        if(SecurityContextHolder.getContext().getAuthentication() != null)
            claims.setSub(SecurityContextHolder.getContext().getAuthentication().getName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null)
            throw new IllegalStateException("[createRefreshAndAccessToken] User is not Authenticated");

        String refreshToken = Utils.generateRefreshToken();
        String username = authentication.getName();

        String refreshKey = "refreshToken:"+username+":"+refreshToken;
        redisTemplate.opsForValue().set(
                refreshKey,
                new Object(),
                1,
                TimeUnit.DAYS);

        String accessToken = jwtTokenService.getJwt(claims);

        return new AccessAndRefreshToken(accessToken, refreshToken);
    }

}
