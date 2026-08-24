package org.sageDelta.auth_service.security.beans;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.entity.ContactDetailsEntity;
import org.sageDelta.auth_service.entity.UsersEntity;
import org.sageDelta.auth_service.enums.ProviderEnum;
import org.sageDelta.auth_service.repositories.ContactDetailsRepository;
import org.sageDelta.auth_service.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final ContactDetailsRepository contactDetailsRepository;


    @Override
    public void onAuthenticationSuccess(
          HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;

        OAuth2User oidcUser = oauthToken.getPrincipal();
        log.info("openId connect username {}and email: {}",
                oidcUser.getAttribute("name"), oidcUser.getAttribute("email"));

/*        String email = oidcUser.getEmail();
        String[] name = oidcUser.getName().split(" ");
        UsersEntity user = new UsersEntity();

        if(contactDetailsRepository.existsByEmail(email)){
            Optional<UsersEntity> usersEntityOptional = contactDetailsRepository.findByEmail(email);
            if(usersEntityOptional.isPresent()){
                principal = Authentication.Builder().build();
            }
        }

        ContactDetailsEntity contactDetails = new ContactDetailsEntity();
        contactDetails.setEmail(oidcUser.getEmail());

        user.setFirstName(name[0]);
        user.setUsername(oidcUser.getEmail());

        if(name.length > 1){
            user.setLastName(name[1]);
        }

        user.setProvider(ProviderEnum.GOOGLE.getName());
*/

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            log.info("Saved request URL = {}", savedRequest.getRedirectUrl());
        }

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
