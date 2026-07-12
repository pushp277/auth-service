package com.sageDelta.auth_service.controllers;

import com.sageDelta.auth_service.models.createToken.CreateTokenRequest;
import com.sageDelta.auth_service.models.createToken.CreateTokenResponse;
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
    @ResponseBody
    default ResponseEntity<CreateUserResponse> createUser(
            @Valid
            @ModelAttribute
            CreateUserRequest createUserRequest){
        return ResponseEntity.ok(getAuthDeligate().create(createUserRequest));
    }

    @PostMapping("/refresh")
    @ResponseBody
    default ResponseEntity<RefreshResponse> refreshUser(
            @Valid
            @RequestBody
            RefreshRequest refreshRequest
    ){
        return ResponseEntity.ok(getAuthDeligate().refresh());
    }

    @PostMapping("/authorize")
    default String authorizeUser(
            @Valid
            @RequestBody
            LoginUserRequest loginUserRequest
    ){

        return "redirect:"+getAuthDeligate().authorize(loginUserRequest);
    }

    @GetMapping("/token")
    @ResponseBody
    default ResponseEntity<CreateTokenResponse> token(
            @Valid
            CreateTokenRequest createTokenRequest
    ){
        return ResponseEntity.ok(getAuthDeligate().token(createTokenRequest));
    }

    @PostMapping("/logout")
    @ResponseBody
    default ResponseEntity<LogoutUserResponse> logoutUser(
            @Valid
            @RequestBody
            LogoutUserRequest logoutUserRequest
    ){
        return ResponseEntity.ok(getAuthDeligate().logout());
    }
}
