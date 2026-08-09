package org.sageDelta.auth_service.security.config;

import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.entity.UsersEntity;
import org.sageDelta.auth_service.exceptions.authorize.UserDoesNotExist;
import org.sageDelta.auth_service.repositories.UserRepository;
import org.sageDelta.auth_service.security.beans.LoginEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@Slf4j
public class LoginSecurityConfig {

    @Bean
    public SecurityFilterChain loginSecurityFilterChain(HttpSecurity http, LoginEntryPoint loginEntryPoint) {

        log.info("Security is enabled for login");
        return http
                .cors((cors)->cors.configurationSource(corsConfigurationSource()))
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/api/v1/logout", "/api/v1/default", "/api/v1/login",
                                        "/api/v1/logout/**", "/api/v1/create/**").permitAll()

                                .anyRequest().authenticated())
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(loginEntryPoint);
                })
                .formLogin((form) -> form
                        .loginProcessingUrl("/api/v1/login"))

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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Allows all origins (use setAllowedOriginPatterns if setAllowCredentials is true)
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
