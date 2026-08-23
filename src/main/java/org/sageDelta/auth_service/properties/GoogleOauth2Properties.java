package org.sageDelta.auth_service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.security.oauth2.client.registration.google")
public record GoogleOauth2Properties(
        String scope,
        String redirectUri,
        String endpoint
) {
}
