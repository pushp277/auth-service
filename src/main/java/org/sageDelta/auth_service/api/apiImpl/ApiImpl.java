package org.sageDelta.auth_service.api.apiImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.api.ApiApi;
import org.sageDelta.auth_service.model.*;
import org.sageDelta.auth_service.services.userService.AuthUiService;
import org.sageDelta.auth_service.services.userService.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.net.URI;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApiImpl implements ApiApi {

    private final AuthUiService userService;
    final ClientService clientService;

    @Override
    public ResponseEntity<Void> authorizeUser(String clientId,
                                             String redirectUri,
                                             String session,
                                             String scope){

            URI redirectUrl = clientService.authorize(clientId, redirectUri, session, scope);

            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(redirectUrl)
                    .build();

    }

    @Override
    public ResponseEntity<CreateTokenResponse> exchangeToken(CreateTokenRequest request){
        CreateTokenResponse response = clientService.createToken(request);
        return ResponseEntity.ok(response);
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
    public ResponseEntity<RefreshTokenResponse> generateRefreshToken(RefreshTokenRequest request){

       return ResponseEntity.ok(
               clientService.refreshToken(request)
       );
    }

    @Override
    public ResponseEntity<TimestampResponse> timestamp(){

        TimestampResponse response = new TimestampResponse();

        response.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.ok(
            response
        );
    }

}
