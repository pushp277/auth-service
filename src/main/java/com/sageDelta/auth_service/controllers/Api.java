package com.sageDelta.auth_service.controllers;

import com.sageDelta.auth_service.models.createUser.CreateUserRequest;
import com.sageDelta.auth_service.models.createUser.CreateUserResponse;
import com.sageDelta.auth_service.models.loginUser.LoginUserRequest;
import com.sageDelta.auth_service.models.loginUser.LoginUserResponse;
import com.sageDelta.auth_service.models.logoutUser.LogoutUserRequest;
import com.sageDelta.auth_service.models.logoutUser.LogoutUserResponse;
import com.sageDelta.auth_service.models.refresh.RefreshRequest;
import com.sageDelta.auth_service.models.refresh.RefreshResponse;
import com.sageDelta.auth_service.services.AuthDeligate;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Raj, Pushp
 * endpoints to authenticate client
 *
 */
@RequestMapping("api/v1")
public interface Api {
    AuthDeligate getAuthDeligate();

    @PostMapping("/create")
    default ResponseEntity<CreateUserResponse> createUser(
            @Valid
            @ModelAttribute
            CreateUserRequest createUserRequest){
        return ResponseEntity.ok(getAuthDeligate().create(createUserRequest));
    }

    @PostMapping("/refresh")
    default ResponseEntity<RefreshResponse> refreshUser(
            @Valid
            @RequestBody
            RefreshRequest refreshRequest
    ){
        return ResponseEntity.ok(getAuthDeligate().refresh());
    }

    @PostMapping("/login")
    default ResponseEntity<LoginUserResponse> loginUser(
            @Valid
            @RequestBody
            LoginUserRequest loginUserRequest
    ){
        return ResponseEntity.ok(getAuthDeligate().login());
    }

    @PostMapping("/logout")
    default ResponseEntity<LogoutUserResponse> logoutUser(
            @Valid
            @RequestBody
            LogoutUserRequest logoutUserRequest
    ){
        return ResponseEntity.ok(getAuthDeligate().logout());
    }
}
