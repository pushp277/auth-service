package org.sageDelta.auth_service.validators;

import org.sageDelta.auth_service.annotations.UniqueEmail;
import org.sageDelta.auth_service.repositories.ContactDetailsRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
   private final ContactDetailsRepository contactDetailsRepository;

   @Override
    public boolean isValid(String email, ConstraintValidatorContext ctx){

       return !contactDetailsRepository.existsByEmail(email);
   }

}
