package org.sageDelta.auth_service.security.beans;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sageDelta.auth_service.configs.ClientUIConfig;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginEntryPoint implements AuthenticationEntryPoint {

    private final ClientUIConfig clientUIConfig;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException{

        String query = request.getQueryString();
        String loginPageUri = clientUIConfig.url()+'?'+query+"&redirect_endpoint=/oauth2/authorize";

        response.sendRedirect(loginPageUri);
    }
}
