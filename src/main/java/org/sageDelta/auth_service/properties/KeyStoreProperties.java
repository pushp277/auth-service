package org.sageDelta.auth_service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("key-store")
public record KeyStoreProperties(String algorithm,
                                 String privateKey,
                                 String publicKey) {

    public KeyStoreProperties {
        privateKey = privateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        publicKey = publicKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
    }
}
