package com.sageDelta.auth_service.models.createToken;

import jakarta.validation.constraints.NotNull;

public record CreateTokenRequest(
        @NotNull
        String exchangeCode) {
}
