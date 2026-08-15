package org.sageDelta.auth_service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("id-provider-ui")
public record IdProviderUiProperties(String url) {
}
