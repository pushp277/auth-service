package org.sageDelta.auth_service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("spring.session")
public record SessionProperties(
        Duration timeout,
        Duration rememberMeTimeout,
        String rememberMeSecret) {
}
