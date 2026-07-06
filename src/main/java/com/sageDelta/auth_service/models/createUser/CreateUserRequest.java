package com.sageDelta.auth_service.models.createUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

/**
 * Onboarding user to sageDelta system
 * @param name
 * @param dateOfBirth
 * @param password
 * @param contactDetail
 */
public record CreateUserRequest(
        @NotBlank
        String name,
        @NotNull
        Date dateOfBirth,
        @NotBlank
        String password,

        @NotNull
        ContactDetails contactDetail
){

    /**
     *
     * @param address
     * @param phoneNumber
     * @param email
     */
    record ContactDetails(Address address,
                           String phoneNumber,
                           String email){

        /**
         *
         * @param prefecture
         * @param city
         * @param line1
         * @param line2
         * @param postalCode
         */
        record Address(String prefecture,
                       String city,
                       String line1,
                       String line2,
                       int postalCode){}
    }
}
