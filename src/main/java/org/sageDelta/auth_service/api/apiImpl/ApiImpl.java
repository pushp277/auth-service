package org.sageDelta.auth_service.api.apiImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.api.ApiApi;
import org.sageDelta.auth_service.clients.BasicClient;
import org.sageDelta.auth_service.properties.IdProviderUiProperties;
import org.sageDelta.auth_service.exceptions.clients.ClientNotFoundException;
import org.sageDelta.auth_service.exceptions.clients.ClientUrlNotFoundException;
import org.sageDelta.auth_service.model.CreateTokenRequest;
import org.sageDelta.auth_service.model.CreateTokenResponse;
import org.sageDelta.auth_service.model.User;
import org.sageDelta.auth_service.services.userService.AuthUiService;
import org.sageDelta.auth_service.services.userService.ClientService;
import org.sageDelta.auth_service.utils.Utils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApiImpl implements ApiApi {

    private final AuthUiService userService;
    private  final ClientService clientService;

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
    public ResponseEntity<Void> logoutUser(String session){

        return ResponseEntity.badRequest().build();
    }

}
