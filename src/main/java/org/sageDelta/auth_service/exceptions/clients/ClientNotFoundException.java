package org.sageDelta.auth_service.exceptions.clients;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
