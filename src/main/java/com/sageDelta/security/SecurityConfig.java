package com.sageDelta.security;

import com.sageDelta.security.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AdminConfig adminConfig;

    public SecurityConfig(AdminConfig adminConfig){
       this.adminConfig = adminConfig;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChainAdmin(HttpSecurity http){
        return http.securityMatcher("/api/admin").
                csrf(CsrfConfigurer::disable)
                        .authorizeHttpRequests(req -> {
                        req.requestMatchers("/**")
                                .hasRole("ADMIN");
                }).
                httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http.csrf(CsrfConfigurer::disable).
                authorizeHttpRequests(request -> {
                    request.anyRequest().permitAll();
        }).build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        UserDetails admin = User.builder()
                .username(adminConfig.user())
                .password(passwordEncoder.encode(adminConfig.password()))
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager(admin);
    }

}
