package com.sageDelta.auth_service.services;

import com.sageDelta.auth_service.models.createUser.CreateUserResponse;
import com.sageDelta.auth_service.models.verifyUser.VerifyUserResponse;

public interface AuthDeligate {
    CreateUserResponse create();
    VerifyUserResponse verify();
}
