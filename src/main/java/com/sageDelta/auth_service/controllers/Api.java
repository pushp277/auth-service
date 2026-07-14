package com.sageDelta.auth_service.controllers;

import com.sageDelta.auth_service.models.createToken.CreateTokenRequest;
import com.sageDelta.auth_service.models.createToken.CreateTokenResponse;
import com.sageDelta.auth_service.models.createUser.CreateUserRequest;
import com.sageDelta.auth_service.models.createUser.CreateUserResponse;
import com.sageDelta.auth_service.models.loginUser.LoginUserRequest;
import com.sageDelta.auth_service.models.logoutUser.LogoutUserRequest;
import com.sageDelta.auth_service.models.refresh.RefreshRequest;
import com.sageDelta.auth_service.models.refresh.RefreshResponse;
import com.sageDelta.auth_service.services.AuthDeligate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
            LoginUserRequest loginUserRequest,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);

        if(session == null){
           return "redirect:login" ;
        }

        request.getSession();

    }

    @GetMapping("/token")
    @ResponseBody
    default ResponseEntity<CreateTokenResponse> token(
            @Valid
            CreateTokenRequest createTokenRequest
    ){
        return ResponseEntity.ok(getAuthDeligate().token(createTokenRequest));
    }

    @PostMapping("/login")
    default String loginUser(
            @Valid
            @RequestBody
            LoginUserRequest loginUserRequest){
        return getAuthDeligate().login(loginUserRequest);
    }

    @PostMapping("/logout")
    default String logoutUser(
            @Valid
            @RequestBody
            LogoutUserRequest logoutUserRequest
    ){
        return "redirect:logout";
    }
}
