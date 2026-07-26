package org.sageDelta.auth_service.validators;

import org.sageDelta.auth_service.annotations.UniqueUsers;
import org.sageDelta.auth_service.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UniqueUserValidator implements ConstraintValidator<UniqueUsers, String> {

    private final UserRepository userRepository;
    @Override
    public boolean isValid(String user, ConstraintValidatorContext context){
        return !userRepository.existsByUsername(user);
    }
}
