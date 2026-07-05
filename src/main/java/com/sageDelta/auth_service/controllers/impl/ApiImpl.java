package com.sageDelta.auth_service.controllers.impl;

import com.sageDelta.auth_service.controllers.Api;
import com.sageDelta.auth_service.services.AuthDeligate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiImpl implements Api {
    private final AuthDeligate authDeligate;

    @Override
    public AuthDeligate getAuthDeligate() {
        return authDeligate;
    }
}
