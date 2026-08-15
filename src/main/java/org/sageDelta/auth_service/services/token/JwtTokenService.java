package org.sageDelta.auth_service.services.token;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService {

    private final ECDSASigner signer;
    private final ObjectMapper objectMapper;

    public String getJwt(Object payloadData) {

        if(payloadData == null)
            throw new IllegalArgumentException("payloadData is null");

        try {
            JWSHeader header = new JWSHeader
                    .Builder(JWSAlgorithm.ES256)
                    .type(new JOSEObjectType("JWT"))
                    .build();



            Map<String, Object> claimsMap = objectMapper.convertValue(
                    payloadData,
                    new TypeReference<Map<String, Object>>() {}
            );

            JWTClaimsSet claimsSet = JWTClaimsSet.parse(claimsMap);

            SignedJWT signedJWT = new SignedJWT(header,claimsSet);

            signedJWT.sign(signer);

            return signedJWT.serialize();
        }
        catch(Exception ex){
            log.error("message: {}", ex.getMessage());
            throw new RuntimeException("getJwt doesn't execute");
        }
    }

}
