package org.sageDelta.auth_service.exceptions.clients;

public class ClientUrlNotFoundException extends RuntimeException {
    public ClientUrlNotFoundException(String message) {
        super(message);
    }
}
