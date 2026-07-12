package com.sageDelta.auth_service.models.refresh;

import jakarta.validation.constraints.NotNull;

public record RefreshRequest(
        @NotNull
        String accessToken
) {
}
