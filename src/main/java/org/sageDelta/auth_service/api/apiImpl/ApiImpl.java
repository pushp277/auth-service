package org.sageDelta.auth_service.api.apiImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.api.ApiApi;
import org.sageDelta.auth_service.configs.ClientUIConfig;
import org.sageDelta.auth_service.model.User;
import org.sageDelta.auth_service.services.userService.AuthUiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApiImpl implements ApiApi {

    private final AuthUiService userService;
    private final ClientUIConfig clientUIConfig;

    @Override
    public ResponseEntity<Void> authorizeUser(
           String clientId,
           String redirectUri,
           String session,
            String scope
    ) {

            URI redirectUrl = UriComponentsBuilder
                    .fromUriString(redirectUri)
                    .queryParam("code", "123")
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
