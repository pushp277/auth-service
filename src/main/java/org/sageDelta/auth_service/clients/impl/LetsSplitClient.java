package org.sageDelta.auth_service.clients.impl;

import org.sageDelta.auth_service.clients.BasicClient;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Set;

@ConfigurationProperties("clients.lets-split")
public record LetsSplitClient(String clientId,
                              List<String> redirectUrls,
                              String clientSecret) implements BasicClient {

}
