package com.chetan.productapi.service;

import com.chetan.productapi.dto.request.LoginRequest;
import com.chetan.productapi.dto.request.RefreshRequest;
import com.chetan.productapi.dto.request.RegisterRequest;
import com.chetan.productapi.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshRequest request);
}