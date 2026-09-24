package com.transparemploi.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.AuthResponse;
import com.transparemploi.backend.dto.LoginRequest;
import com.transparemploi.backend.dto.RegisterRequest;
import com.transparemploi.backend.dto.UserResponse;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.security.JwtService;
import com.transparemploi.backend.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {

        User created = authService.register(request);

        String token = jwtService.generateToken(created.getEmail());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(created.getId());
        userResponse.setEmail(created.getEmail());
        userResponse.setRole(created.getRole());

        return ResponseEntity.ok(new AuthResponse(token, userResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        User logged = authService.login(request);

        String token = jwtService.generateToken(logged.getEmail());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(logged.getId());
        userResponse.setEmail(logged.getEmail());
        userResponse.setRole(logged.getRole());

        return ResponseEntity.ok(new AuthResponse(token, userResponse));
    }
}
