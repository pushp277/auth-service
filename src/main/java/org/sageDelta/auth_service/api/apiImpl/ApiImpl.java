package org.sageDelta.auth_service.api.apiImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.api.ApiApi;
import org.sageDelta.auth_service.exceptions.authorize.UserDoesNotExist;
import org.sageDelta.auth_service.model.LoginRequest;
import org.sageDelta.auth_service.model.LoginResponse;
import org.sageDelta.auth_service.model.User;
import org.sageDelta.auth_service.services.createUserService.AuthUiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApiImpl implements ApiApi {

    private final AuthUiService userService;

    @Override
    public ResponseEntity<Void> authorizeUser(
           String clientId,
           String redirectUri,
           String sessionCookie,
            String scope
    ) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
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
    public ResponseEntity<LoginResponse> loginUser(LoginRequest request){

        if(request == null)
            throw new UserDoesNotExist("Please enter username and password");

        String authCode = userService.verifyUser(request.getUsername(), request.getPassword());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessCode(authCode);

        return ResponseEntity
                .ok()
                .body(loginResponse);
    }

}
