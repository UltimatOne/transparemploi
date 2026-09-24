package com.transparemploi.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.transparemploi.backend.dto.LoginRequest;
import com.transparemploi.backend.dto.RegisterRequest;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    // ---------------------------
    // REGISTER
    // ---------------------------
    public User register(RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER") // rôle automatique
                .build();

        return repository.save(user);
    }

    // ---------------------------
    // LOGIN
    // ---------------------------
    public User login(LoginRequest request) {

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

        return user;
    }
}
