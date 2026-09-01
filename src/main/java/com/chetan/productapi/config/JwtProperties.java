package com.chetan.productapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "spring.security.jwt")   // ✅ added "spring."
@Data
public class JwtProperties {
    private String secret;
    private long accessTokenExpiration;   // in milliseconds
    private long refreshTokenExpiration;  // in milliseconds
    
    @PostConstruct
    public void init() {
        System.out.println("JWT Secret: " + secret);
        System.out.println("Access Token Expiration: " + accessTokenExpiration);
        System.out.println("Refresh Token Expiration: " + refreshTokenExpiration);
    }
}