package com.sageDelta.auth_service.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.data.redis")
public record JedisConfigProperties(String host,
                                    int port,
                                    long timeout,
                                    int maxIdle,
                                    int minIdle,
                                    int totalPool) {
}
