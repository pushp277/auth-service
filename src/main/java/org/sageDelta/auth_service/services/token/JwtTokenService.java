package org.sageDelta.auth_service.services.token;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.dao.LoginUserDetail;
import org.sageDelta.auth_service.model.JWTClaims;
import org.sageDelta.auth_service.properties.KeyStoreProperties;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService {

    private final ECDSASigner signer;
    private final JWSHeader header;
    private final KeyStoreProperties keyStoreProperties;

    public String getJwt(JWTClaims claims, LoginUserDetail loginUser) {

        long expTime = System.currentTimeMillis() + keyStoreProperties.accessTokenExpiry().toMillis();
        Date ist = new Date();
        Date exp = new Date(expTime);
        log.info("issue date: {} expiry date: {}", ist, expTime);


        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .jwtID(claims.getJti())
                    .subject(loginUser.getUsername())
                    .audience(claims.getAud())
                    .issuer(claims.getIss())
                    .issueTime(ist)
                    .claim("role", claims.getRole())
                    .claim("email", loginUser.getEmail())
                    .claim("provider", loginUser.getProvider())
                    .expirationTime(exp)
                    .build();

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
