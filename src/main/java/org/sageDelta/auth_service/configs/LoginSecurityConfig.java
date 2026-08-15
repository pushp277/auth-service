package org.sageDelta.auth_service.configs;

import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.entity.UsersEntity;
import org.sageDelta.auth_service.exceptions.authorize.UserDoesNotExist;
import org.sageDelta.auth_service.repositories.UserRepository;
import org.sageDelta.auth_service.security.beans.LoginEntryPoint;
import org.sageDelta.auth_service.properties.SessionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@Slf4j
public class LoginSecurityConfig {

    @Bean
    public SecurityFilterChain loginSecurityFilterChain(HttpSecurity http,
                                                        LoginEntryPoint loginEntryPoint,
                                                        SessionProperties sessionConfig) {

        log.info("Security is enabled for login");
        return http
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/api/v1/logout", "/api/v1/default", "/api/v1/login",
                                        "/api/v1/logout/**", "/api/v1/create/**").permitAll()

                                .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Allows HTTP session creation
                )
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(loginEntryPoint);
                })
                .formLogin((form) -> form
                        .loginProcessingUrl("/api/v1/login"))
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


}
