package com.sageDelta.auth_service;

import com.sageDelta.security.config.AdminConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages =
		{"com.sageDelta.auth_service",
		"com.sageDelta.security"})
@EnableConfigurationProperties({AdminConfig.class})
public class AuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}
