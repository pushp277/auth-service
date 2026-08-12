package org.sageDelta.auth_service.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("spring.session")
public record SessionConfig(
        Duration timeout,
        Duration rememberMeTimeout,
        String rememberMeSecret) {
}
