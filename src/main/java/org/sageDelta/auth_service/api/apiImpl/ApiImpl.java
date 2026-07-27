package org.sageDelta.auth_service.api.apiImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.api.ApiApi;
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
    public ResponseEntity<Void> createNewUser(String uri, User user){
        log.info("createNewUser requested");
        userService.createUser(user);

        ResponseEntity<Void> responseEntity = ResponseEntity
                .status(HttpStatus.FOUND)
                .build();

        return responseEntity;
    }
}
