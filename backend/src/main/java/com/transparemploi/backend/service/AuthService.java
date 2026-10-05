package com.transparemploi.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.transparemploi.backend.dto.AuthResponseDTO;
import com.transparemploi.backend.dto.LoginRequest;
import com.transparemploi.backend.dto.RegisterRequest;
import com.transparemploi.backend.model.RefreshToken;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.repository.UserRepository;
import com.transparemploi.backend.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    // ---------------------------
    // REGISTER
    // ---------------------------
    public User register(RegisterRequest request) {

        System.out.println("🟠 SERVICE → register() called");
        System.out.println("🟠 SERVICE → Email = " + request.getEmail());

        if (repository.existsByEmail(request.getEmail())) {
            System.out.println("🔴 SERVICE → Email already exists");
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        User saved = repository.save(user);

        System.out.println("🟢 SERVICE → User registered with id = " + saved.getId());

        return saved;
    }

    // ---------------------------
    // LOGIN
    // ---------------------------
    public AuthResponseDTO login(LoginRequest request) {

        System.out.println("🟠 SERVICE → login() called");
        System.out.println("🟠 SERVICE → Email = " + request.getEmail());
        System.out.println("🟠 SERVICE → Raw password = " + request.getPassword());

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    System.out.println("🔴 SERVICE → User NOT FOUND");
                    return new IllegalArgumentException("Utilisateur introuvable");
                });

        System.out.println("🟠 SERVICE → User found in DB = " + user.getEmail());
        System.out.println("🟠 SERVICE → Hashed password = " + user.getPassword());

        boolean match = passwordEncoder.matches(request.getPassword(), user.getPassword());
        System.out.println("🟠 SERVICE → Password match = " + match);

        if (!match) {
            System.out.println("🔴 SERVICE → Password INCORRECT");
            throw new IllegalArgumentException("Mot de passe incorrect");
        }

        System.out.println("🟢 SERVICE → Password OK");

        // ---------------------------
        // ACCESS TOKEN
        // ---------------------------
        String jwt = jwtService.generateToken(user);
        System.out.println("🟢 SERVICE → JWT generated = " + jwt);

        // ---------------------------
        // REFRESH TOKEN
        // ---------------------------
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        System.out.println("🟢 SERVICE → Refresh token generated = " + refreshToken.getToken());

        // ---------------------------
        // RESPONSE DTO
        // ---------------------------
        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(jwt);
        response.setRefreshToken(refreshToken.getToken());
        response.setRefreshTokenExpiry(refreshToken.getExpiryDate().toEpochMilli());
        response.setRole(user.getRole());

        System.out.println("🟢 SERVICE → AuthResponseDTO ready, returning to controller");

        return response;
    }

    // ---------------------------
    // REFRESH TOKEN
    // ---------------------------
    public AuthResponseDTO refresh(String refreshToken) {

        System.out.println("🟠 SERVICE → refresh() called");
        System.out.println("🟠 SERVICE → Incoming refresh token = " + refreshToken);

        if (refreshToken == null || refreshToken.isBlank()) {
            System.out.println("🔴 SERVICE → No refresh token provided");
            throw new IllegalArgumentException("Refresh token manquant");
        }

        RefreshToken storedToken = refreshTokenService.validateRefreshToken(refreshToken);

        System.out.println("🟢 SERVICE → Refresh token valid for user = " + storedToken.getUserEmail());

        User user = repository.findByEmail(storedToken.getUserEmail())
                .orElseThrow(() -> {
                    System.out.println("🔴 SERVICE → User not found for refresh token");
                    return new IllegalArgumentException("Utilisateur introuvable");
                });

        // ---------------------------
        // NEW ACCESS TOKEN
        // ---------------------------
        String newJwt = jwtService.generateToken(user);
        System.out.println("🟢 SERVICE → New JWT generated = " + newJwt);

        // ---------------------------
        // NEW REFRESH TOKEN
        // ---------------------------
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        System.out.println("🟢 SERVICE → New refresh token generated = " + newRefreshToken.getToken());

        // ---------------------------
        // RESPONSE DTO
        // ---------------------------
        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(newJwt);
        response.setRefreshToken(newRefreshToken.getToken());
        response.setRefreshTokenExpiry(newRefreshToken.getExpiryDate().toEpochMilli());
        response.setRole(user.getRole());

        System.out.println("🟢 SERVICE → AuthResponseDTO ready (refresh)");

        return response;
    }
}
