package org.sageDelta.auth_service.security.beans;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.dao.LoginUserDetail;
import org.sageDelta.auth_service.entity.ContactDetailsEntity;
import org.sageDelta.auth_service.entity.UsersEntity;
import org.sageDelta.auth_service.enums.ProviderEnum;
import org.sageDelta.auth_service.repositories.ContactDetailsRepository;
import org.sageDelta.auth_service.repositories.UserRepository;
import org.sageDelta.auth_service.utils.Utils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
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


        ProviderEnum provider = ProviderEnum.NIL;

        OAuth2User oidcUser = oauthToken.getPrincipal();
        String user = oidcUser.getAttribute("name");
        String userEmail = oidcUser.getAttribute("email");
        String oauth2Provider = oauthToken.getAuthorizedClientRegistrationId();

        if(oauth2Provider.equalsIgnoreCase("google"))
            provider = ProviderEnum.GOOGLE;

        if(oauth2Provider.equalsIgnoreCase("github"))
            provider = ProviderEnum.GITHUB;

        log.info("openId connect username {}, provider: {} and email: {}",
                user, provider.getName(), userEmail);

        if(user == null ||  userEmail == null) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        Optional<ContactDetailsEntity> contactDetailsEntityOptional = contactDetailsRepository.findByEmail(userEmail);


        Authentication currAuth =  SecurityContextHolder.getContext().getAuthentication();


        if(contactDetailsEntityOptional.isPresent()) {

            Optional<UsersEntity> usersEntityOptional = userRepository.findByContactDetails(contactDetailsEntityOptional.get());
            if (usersEntityOptional.isPresent()) {

                LoginUserDetail userDetail = LoginUserDetail.builder()
                        .username(usersEntityOptional.get().getUsername())
                        .provider(provider)
                        .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                        .email(userEmail)
                        .build();

                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        userDetail,
                        currAuth.getCredentials(),
                        userDetail.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(newAuth);
                super.onAuthenticationSuccess(request,response,authentication);
                return;
            }
        }

        ContactDetailsEntity contactDetailsEntity = new ContactDetailsEntity();
        UsersEntity usersEntity = new UsersEntity();

        contactDetailsEntity.setEmail(userEmail);
        ContactDetailsEntity savedContactDetail = contactDetailsRepository.save(contactDetailsEntity);


        String[] userContent = user.split(" ");

        String userName = userContent[0] + Utils.generateSalt();

        usersEntity.setUsername(userName);
        usersEntity.setPassword(Utils.generateSalt());
        usersEntity.setFirstName(userContent[0]);

        if(userContent.length > 1){
            usersEntity.setLastName(userContent[1]);
        }
        usersEntity.setProvider(provider.getName());
        usersEntity.setContactDetails(savedContactDetail);

        userRepository.save(usersEntity);

        LoginUserDetail userDetail = LoginUserDetail
                .builder()
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .username(userName)
                .provider(provider)
                .email(userEmail)
                .build();


        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                userDetail,
                currAuth.getCredentials(),
                userDetail.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
