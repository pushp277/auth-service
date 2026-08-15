package org.sageDelta.auth_service.api;

import org.sageDelta.auth_service.exceptions.authorize.UserDoesNotExist;
import org.sageDelta.auth_service.exceptions.authorize.UserPasswordIsWrong;
import org.sageDelta.auth_service.exceptions.clients.ClientNotFoundException;
import org.sageDelta.auth_service.exceptions.create.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> userAlreadyExistException(Exception ex){
        return ResponseEntity
                .badRequest()
                .body("User already exists");
    }

    @ExceptionHandler(UserPasswordIsWrong.class)
    public ResponseEntity<String> wrongUserPassword(Exception ex){

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserDoesNotExist.class)
    public ResponseEntity<String> userDoesNotExist(Exception ex){
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> clientNotFound(Exception ex){

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
