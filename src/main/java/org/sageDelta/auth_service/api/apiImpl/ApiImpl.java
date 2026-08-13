package org.sageDelta.auth_service.api.apiImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.api.ApiApi;
import org.sageDelta.auth_service.clients.BasicClient;
import org.sageDelta.auth_service.configs.ClientUIConfig;
import org.sageDelta.auth_service.exceptions.clients.ClientNotFoundException;
import org.sageDelta.auth_service.exceptions.clients.ClientUrlNotFoundException;
import org.sageDelta.auth_service.model.User;
import org.sageDelta.auth_service.services.userService.AuthUiService;
import org.sageDelta.auth_service.utils.Utils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApiImpl implements ApiApi {

    private final AuthUiService userService;
    private final ClientUIConfig clientUIConfig;
    private final RedisTemplate<String, String> redisTemplate;
    private final List<BasicClient> clients;

    @Override
    public ResponseEntity<Void> authorizeUser(
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
                    client.clientId()+"::"+authCode,
                    "",
                    60,
                    TimeUnit.SECONDS
            );

            URI redirectUrl = UriComponentsBuilder
                    .fromUriString(findRedirectUrl)
                    .queryParam("code", authCode)
                    .queryParam("scope", scope)
                    .build()
                    .toUri();

            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(redirectUrl)
                    .build();


    }

    @Override
    public ResponseEntity<Void> createNewUser(User user){
        log.info("createNewUser requested");
        userService.createUser(user);

        ResponseEntity<Void> responseEntity = ResponseEntity
                .ok()
                .build();

        return responseEntity;
    }

    @Override
    public ResponseEntity<Void> logoutUser(String session){

        return ResponseEntity.badRequest().build();
    }

}
