package com.sageDelta.auth_service.models.createUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

/**
 * Onboarding user to sageDelta system
 * @param firstName
 * @param lastName
 * @param dateOfBirth
 * @param password
 * @param contactDetails
 */
public record CreateUserRequest(
        @NotBlank(message = "username is required")
        String username,

        @NotBlank(message = "firstName is required")
        String firstName,

        @NotBlank(message ="lastName is required")
        String lastName,

        @NotNull(message = "dateOfBirth is required")
        Date dateOfBirth,
        @NotBlank(message =" password is required")
        String password,

        ContactDetails contactDetails
){

    /**
     *
     * @param address
     * @param phoneNumber
     * @param email
     */
    public record ContactDetails(Address address,
                           String phoneNumber,
                           @Email
                           String email){

        public ContactDetails{

        }
        /**
         *
         * @param prefecture
         * @param city
         * @param line1
         * @param line2
         * @param postalCode
         */

        public record Address(String prefecture,
                       String country,
                       String city,
                       String line1,
                       String line2,
                       long postalCode){

        }
    }
}
