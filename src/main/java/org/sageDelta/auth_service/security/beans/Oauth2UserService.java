package org.sageDelta.auth_service.security.beans;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Oauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
        private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        private final RestClient restClient = RestClient.create();

        @Override
        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            OAuth2User oauth2User = delegate.loadUser(userRequest);
            String registrationId = userRequest.getClientRegistration().getRegistrationId();

            // If it's GitHub and email is missing, fetch it via the /user/emails API
            if ("github".equals(registrationId)) {
                String email = oauth2User.getAttribute("email");

                if (email == null) {
                    email = fetchPrimaryGitHubEmail(userRequest.getAccessToken().getTokenValue());
                }

                // Inject the email back into the attributes map so it's accessible globally
                Map<String, Object> modifiedAttributes = new HashMap<>(oauth2User.getAttributes());
                if (email != null) {
                    modifiedAttributes.put("email", email);
                }

                return new DefaultOAuth2User(
                        oauth2User.getAuthorities(),
                        modifiedAttributes,
                        "id" // or whatever attribute key GitHub uses for name (e.g., "login" or "id")
                );
            }

            return oauth2User;
        }

        private String fetchPrimaryGitHubEmail(String token) {
            try {
                List<GitHubEmailResponse> emails = restClient.get()
                        .uri("https://api.github.com/user/emails")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.ACCEPT, "application/vnd.github+json")
                        .retrieve()
                        .body(new ParameterizedTypeReference<>() {});

                if (emails != null) {
                    return emails.stream()
                            .filter(e -> Boolean.TRUE.equals(e.primary()) && Boolean.TRUE.equals(e.verified()))
                            .map(GitHubEmailResponse::email)
                            .findFirst()
                            .orElse(null);
                }
            } catch (Exception e) {
                // log error if needed
            }
            return null;
        }

        private record GitHubEmailResponse(String email, Boolean primary, Boolean verified) {}
    }
