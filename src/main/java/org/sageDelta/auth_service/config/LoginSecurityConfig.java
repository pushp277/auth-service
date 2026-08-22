package org.sageDelta.auth_service.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.sageDelta.auth_service.entity.UsersEntity;
import org.sageDelta.auth_service.exceptions.authorize.UserDoesNotExist;
import org.sageDelta.auth_service.model.LoginValidationError;
import org.sageDelta.auth_service.properties.ClientsProperties;
import org.sageDelta.auth_service.repositories.UserRepository;
import org.sageDelta.auth_service.security.beans.LoginEntryPoint;
import org.sageDelta.auth_service.properties.SessionProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class LoginSecurityConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain loginSecurityFilterChain(HttpSecurity http,
                                                        LoginEntryPoint loginEntryPoint,
                                                        SessionProperties sessionConfig,
                                                        CorsConfigurationSource configurationSource) {

        log.info("Security is enabled for login");
        return http
                .csrf((csrf)->csrf.disable())
                .cors(cors -> cors.configurationSource(configurationSource))
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/api/v1/logout",
                                        "/api/v1/oauth2/token/**",
                                        "/api/v1/oauth2/refresh/**",
                                        "/api/v1/login",
                                        "/api/v1/logout/**", "/api/v1/create/**").permitAll()

                                .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Allows HTTP session creation
                )
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(loginEntryPoint);
                })
                .formLogin((form) -> form
                        .loginProcessingUrl("/api/v1/login")
                        .failureHandler((request,
                                         response,
                                         exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                            LoginValidationError errorResponse = new LoginValidationError();
                            errorResponse.setMessage("username or password is not correct");

                            objectMapper.writeValue(response.getWriter(), errorResponse);
                        }))
                .oauth2Login(Customizer.withDefaults())
                .logout(logout ->
                        logout
                                .logoutUrl("/api/v1/logout")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("SESSION_","remember-me")
                                .logoutSuccessHandler(((request,
                                                        response,
                                                        authentication) -> {
                                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                                })))

                .rememberMe(remember -> remember
                        .rememberMeParameter("rememberMe")
                        .key(sessionConfig.rememberMeSecret())
                        .tokenValiditySeconds((int)sessionConfig.rememberMeTimeout().toSeconds()))

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        PasswordEncoder passwordEncoder = passwordEncoder();

        return (username) -> {


            Optional<UsersEntity> userEntity = userRepository.findByUsername(username);

            if(userEntity.isEmpty()){
                log.warn("invalid user detail");
                throw new UserDoesNotExist("User doesn't exist");
            }


            return User.builder()
                    .username(userEntity.get().getUsername())
                    .password(userEntity.get().getPassword())
                    .build();
        };

    }

    @Bean
    public CorsConfigurationSource configurationSource(
            ClientsProperties clientsProperties){

        log.info("Cors enabled for: {}", clientsProperties.cors());

       CorsConfiguration configuration = new CorsConfiguration();
       configuration.setAllowedHeaders(List.of("*"));
       configuration.setAllowedMethods(List.of(
               HttpMethod.OPTIONS.name(),
               HttpMethod.POST.name(),
               HttpMethod.GET.name(),
               HttpMethod.PUT.name(),
               HttpMethod.PATCH.name(),
               HttpMethod.DELETE.name()));

       configuration.setAllowedOrigins(clientsProperties.cors());
        configuration.setExposedHeaders(List.of("Authorization", "Location", "Set-Cookie"));
       configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
