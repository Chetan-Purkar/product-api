package com.chetan.productapi.service.impl;

import com.chetan.productapi.config.JwtProperties;
import com.chetan.productapi.dto.request.LoginRequest;
import com.chetan.productapi.dto.request.RefreshRequest;
import com.chetan.productapi.dto.request.RegisterRequest;
import com.chetan.productapi.dto.response.AuthResponse;
import com.chetan.productapi.entity.RefreshToken;
import com.chetan.productapi.entity.User;
import com.chetan.productapi.exception.ResourceNotFoundException;
import com.chetan.productapi.exception.TokenRefreshException;
import com.chetan.productapi.repository.RefreshTokenRepository;
import com.chetan.productapi.repository.UserRepository;
import com.chetan.productapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        // Optionally auto-activate for demo, but keep as pending for production
        // user.setStatus(UserStatus.ACTIVE);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(RefreshRequest request) {
        RefreshToken existingToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid refresh token"));

        if (existingToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(existingToken);
            throw new TokenRefreshException("Refresh token expired");
        }

        User user = existingToken.getUser();

        // Rotate: delete old, create new
        refreshTokenRepository.delete(existingToken);

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .build();
    }

    private String generateRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        long refreshExpiration = jwtProperties.getRefreshTokenExpiration(); // milliseconds

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(System.currentTimeMillis() + refreshExpiration),
                        ZoneId.systemDefault()))
                .build();

        refreshTokenRepository.save(refreshToken);
        return token;
    }
}