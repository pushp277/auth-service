package com.sageDelta.auth_service.models.authorize;

public record AuthorizeRequest(
        String clientId,
        String callback
) {
}
