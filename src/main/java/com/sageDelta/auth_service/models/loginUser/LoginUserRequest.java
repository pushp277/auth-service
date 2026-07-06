package com.sageDelta.auth_service.models.loginUser;

/**
 * authenticating user to sageDelta system
 * @param name
 * @param email
 * @param password
 */
public record LoginUserRequest(String name,
                               String email,
                               String password) {
}
