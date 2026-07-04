package com.sageDelta.auth_service.controllers;

import com.sageDelta.auth_service.models.createUser.CreateUserResponse;
import com.sageDelta.auth_service.models.verifyUser.VerifyUserResponse;
import com.sageDelta.auth_service.services.AuthDeligate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Raj, Pushp
 * endpoint to onboard people
 */
@RequestMapping("api/v1")
public interface Api {
    AuthDeligate getAuthDeligate();

    @PostMapping("user/create")
    default ResponseEntity<CreateUserResponse> createUser(){
        return ResponseEntity.ok(getAuthDeligate().create());
    }

    @GetMapping("user/verify")
    default ResponseEntity<VerifyUserResponse> verifyUser(){
        return ResponseEntity.ok(getAuthDeligate().verify());
    }
}
