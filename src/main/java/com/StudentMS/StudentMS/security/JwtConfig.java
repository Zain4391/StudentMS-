package com.StudentMS.StudentMS.security;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Configurable
public class JwtConfig {
    
    @Value("${jwt.secret:your-secret-key-change-in-production-must-be-at-least-256-bits}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private Long expiration; // in ms

    public String getSecret() {
        return secret;
    }

    public Long getExpiration() {
        return expiration;
    }

}
