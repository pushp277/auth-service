package com.sageDelta.auth_service.models.verifyUser;

/**
 * authenticating user to sageDelta system
 * @param name
 * @param email
 * @param password
 */
public record VerifyUserRequest(String name,
                                String email,
                                String password) {
}
