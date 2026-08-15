package org.sageDelta.auth_service.configs;
import com.nimbusds.jose.crypto.ECDSASigner;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.properties.KeyStoreProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Configuration
@Slf4j
public class EncrypterConfig {

    @Bean
    public ECPrivateKey privateKey(KeyStoreProperties jwtProperties) throws Exception{
        byte[] privateKey = Base64.getDecoder().decode(jwtProperties.privateKey());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("ES");

        return (ECPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    @Bean
    public ECDSASigner signer(ECPrivateKey privateKey) throws Exception{
            return new ECDSASigner(privateKey);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
