package org.sageDelta.auth_service.config;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
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
    public JWSHeader jwsHeader(){
        return new JWSHeader
                .Builder(JWSAlgorithm.ES256)
                .type(new JOSEObjectType("JWT"))
                .build();
    }

    @Bean
    public ECPrivateKey privateKey(KeyStoreProperties jwtProperties) throws Exception{

        byte[] privateKey = Base64.getDecoder().decode(jwtProperties.privateKey());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        return (ECPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    @Bean
    public ECDSASigner signer(ECPrivateKey privateKey) throws Exception{
            return new ECDSASigner(privateKey);
    }

}
