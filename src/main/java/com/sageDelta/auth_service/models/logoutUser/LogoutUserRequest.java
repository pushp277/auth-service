package com.sageDelta.auth_service.models.logoutUser;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @param username
 */
public record LogoutUserRequest(
        @Valid
        @NotNull
        String username) {
}
