package com.sageDelta.auth_service.services;

import com.sageDelta.auth_service.models.createToken.CreateTokenRequest;
import com.sageDelta.auth_service.models.createToken.CreateTokenResponse;
import com.sageDelta.auth_service.models.createUser.CreateUserRequest;
import com.sageDelta.auth_service.models.createUser.CreateUserResponse;
import com.sageDelta.auth_service.models.loginUser.LoginUserRequest;
import com.sageDelta.auth_service.models.loginUser.LoginUserResponse;
import com.sageDelta.auth_service.models.logoutUser.LogoutUserResponse;
import com.sageDelta.auth_service.models.refresh.RefreshResponse;

public interface AuthDeligate {
    CreateUserResponse create(CreateUserRequest createUserRequest);
    String authorize(LoginUserRequest loginUserRequest);
    LogoutUserResponse logout();
    RefreshResponse refresh();
    CreateTokenResponse token(CreateTokenRequest createTokenRequest);
    String login(LoginUserRequest loginUserRequest);
}
