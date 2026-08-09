package org.sageDelta.auth_service.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client-ui")
public record ClientUIConfig(String url) {
}
