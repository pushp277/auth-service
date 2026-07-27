package org.sageDelta.auth_service;

import org.sageDelta.security.config.AdminConfig;
import org.sageDelta.security.config.SecretConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {
		"org.sageDelta.auth_service",
		"org.sageDelta.security"})
@EnableConfigurationProperties({
		AdminConfig.class,
		SecretConfig.class})
@EnableTransactionManagement
public class AuthServiceApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AuthServiceApplication.class, args);
	}
}
