package com.sageDelta.auth_service.services.impl;

import com.sageDelta.auth_service.models.createUser.CreateUserResponse;
import com.sageDelta.auth_service.models.verifyUser.VerifyUserResponse;
import com.sageDelta.auth_service.services.AuthDeligate;
import org.springframework.stereotype.Service;

@Service
public class AuthDeligateService implements AuthDeligate {

    @Override
    public CreateUserResponse create() {
        return null;
    }

    @Override
    public VerifyUserResponse verify(){
        return null;
    }
}
