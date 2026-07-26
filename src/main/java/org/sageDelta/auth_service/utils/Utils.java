package org.sageDelta.auth_service.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class Utils {
    public static String generateSalt(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String getHash(String password, String salt){
        byte[] saltByte = Base64.getDecoder().decode(salt);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(saltByte);
            byte[] hashed = digest.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(hashed);
        }

        catch(NoSuchAlgorithmException exp){
            log.error("Algorithm doesn't exist");
        }

        return null;
    }
}
