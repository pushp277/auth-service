package com.sageDelta.security.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.admin")
public record AdminConfig(String user, String password){};