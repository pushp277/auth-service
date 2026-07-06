package com.sageDelta.auth_service.services;

import com.sageDelta.auth_service.models.createUser.CreateUserResponse;
import com.sageDelta.auth_service.models.loginUser.LoginUserResponse;

public interface AuthDeligate {
    CreateUserResponse create();
    LoginUserResponse verify();
}
