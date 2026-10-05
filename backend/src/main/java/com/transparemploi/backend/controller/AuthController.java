package com.transparemploi.backend.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.AuthResponseDTO;
import com.transparemploi.backend.dto.LoginRequest;
import com.transparemploi.backend.dto.RegisterRequest;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        User created = authService.register(request);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        AuthResponseDTO auth = authService.login(request);

        // ---------------------------
        // COOKIE HTTPONLY (REFRESH TOKEN)
        // ---------------------------
        ResponseCookie cookie = ResponseCookie.from("refreshToken", auth.getRefreshToken())
                .httpOnly(true)
                .secure(false) // mettre true en production
                .path("/api/auth/refresh")
                .maxAge(auth.getRefreshTokenExpiry()) // en secondes
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(auth);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {

        AuthResponseDTO auth = authService.refresh(refreshToken);

        // ---------------------------
        // COOKIE HTTPONLY
        // ---------------------------
        ResponseCookie cookie = ResponseCookie.from("refreshToken", auth.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/api/auth/refresh")
                .maxAge(auth.getRefreshTokenExpiry())
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(auth);
    }
}
