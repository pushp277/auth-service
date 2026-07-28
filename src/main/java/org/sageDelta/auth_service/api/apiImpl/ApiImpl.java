package org.sageDelta.auth_service.api.apiImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.model.LoginRequest;
import org.sageDelta.auth_service.model.User;
import org.sageDelta.auth_service.services.createUserService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApiImpl implements ApiApi {

    private final UserService userService;

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
    public ResponseEntity<Void> loginUser(String host, LoginRequest request){
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", host)
                .build();
    }
}
