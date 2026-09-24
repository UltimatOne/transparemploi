package com.transparemploi.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.transparemploi.backend.dto.AuthResponseDTO;
import com.transparemploi.backend.dto.LoginRequest;
import com.transparemploi.backend.dto.RegisterRequest;
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
        // JWT
        // ---------------------------
        String jwt = jwtService.generateToken(user.getEmail());
        System.out.println("🟢 SERVICE → JWT generated = " + jwt);

        // ---------------------------
        // REFRESH TOKEN
        // ---------------------------
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        System.out.println("🟢 SERVICE → Refresh token generated = " + refreshToken.getToken());

        // ---------------------------
        // RESPONSE DTO
        // ---------------------------
        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(jwt);
        response.setRefreshToken(refreshToken.getToken());
        response.setRefreshTokenExpiry(refreshToken.getExpiryDate().toEpochMilli());
        response.setRole(user.getRole()); // <-- AJOUT DU RÔLE

        System.out.println("🟢 SERVICE → AuthResponseDTO ready, returning to controller");

        return response;
    }
}
