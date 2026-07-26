package org.sageDelta.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.crypt")
public record SecretConfig(String aesKey) {
}
