package com.sageDelta.auth_service.exceptions.authorize;

public class UserDoesNotExist extends RuntimeException {
    public UserDoesNotExist(String message) {
        super(message);
    }

}
