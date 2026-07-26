package org.sageDelta.auth_service.api.apiImpl;

import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.sageDelta.auth_service.api.ApiApi;
import org.sageDelta.auth_service.model.User;
import org.sageDelta.auth_service.services.createUserService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.net.URI;

@Controller
@RequiredArgsConstructor
public class ApiImpl implements ApiApi {

    private final UserService userService;

    @Override
    public ResponseEntity<Void> createNewUser(URI host, User user){
        userService.createUser(user);

        ResponseEntity<Void> responseEntity = ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", "/login")
                .build();

        return responseEntity;
    }
}
