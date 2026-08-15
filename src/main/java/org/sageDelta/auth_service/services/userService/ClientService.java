package org.sageDelta.auth_service.services.userService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.clients.BasicClient;
import org.sageDelta.auth_service.exceptions.clients.ClientNotFoundException;
import org.sageDelta.auth_service.exceptions.clients.ClientUrlNotFoundException;
import org.sageDelta.auth_service.model.CreateTokenRequest;
import org.sageDelta.auth_service.model.CreateTokenResponse;
import org.sageDelta.auth_service.model.JWTClaims;
import org.sageDelta.auth_service.services.token.JwtTokenService;
import org.sageDelta.auth_service.utils.Utils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final RedisTemplate<String, String> redisTemplate;
    private final List<BasicClient> clients;
    private final JwtTokenService jwtTokenService;



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
                    "",
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
        Optional<String> optionalKey = Optional.ofNullable(redisTemplate.opsForValue().get(key));

        if(optionalKey.isEmpty())
            throw new ClientNotFoundException("auth token is wrong or expired");

        CreateTokenResponse response = new CreateTokenResponse();

        //Since the user is authorized created a cache for the user
        JWTClaims claims = new JWTClaims();
        claims.setAud(request.getClientId());
        claims.setJti(request.getAuthorizationCode());
        claims.setRole("USER");
        claims.setIss("SageDelta");

        if(SecurityContextHolder.getContext().getAuthentication() != null)
            claims.setSub(SecurityContextHolder.getContext().getAuthentication().getName());


        response.setAccessToken(jwtTokenService.getJwt(claims));
        response.setRefreshToken(Utils.generateRefreshToken());

        return response;
    }

}
