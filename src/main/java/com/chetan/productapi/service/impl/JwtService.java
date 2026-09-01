package com.chetan.productapi.service.impl;

import com.chetan.productapi.entity.User;
import com.chetan.productapi.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;

    public String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }

    public Long getUserIdFromToken(String token) {
        return jwtUtil.extractUserId(token);
    }

    public String getRoleFromToken(String token) {
        return jwtUtil.extractRole(token);
    }

    public boolean validateToken(String token, User user) {
        return jwtUtil.validateToken(token, user);
    }
}