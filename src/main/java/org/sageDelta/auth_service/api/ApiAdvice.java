package org.sageDelta.auth_service.api;

import org.sageDelta.auth_service.exceptions.create.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> userAlreadyExistException(){
        return ResponseEntity
                .badRequest()
                .body("User already exists");
    }
}
