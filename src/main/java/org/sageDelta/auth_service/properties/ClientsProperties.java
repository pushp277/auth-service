package org.sageDelta.auth_service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("clients")
public record ClientsProperties(List<String> cors) {
}
