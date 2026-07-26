package org.sageDelta.auth_service.exceptions.authorize;

public class UserPasswordIsWrong extends RuntimeException {
    public UserPasswordIsWrong(String message) {
        super(message);
    }
}
