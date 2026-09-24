package com.transparemploi.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.service.RefreshTokenService;

@RestController
public class LogoutController {

    private final RefreshTokenService refreshTokenService;

    public LogoutController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<String> logout(Authentication authentication) {

        String email = authentication.getName(); // email extrait du JWT

        refreshTokenService.deleteRefreshToken(email);

        return ResponseEntity.ok("Logout successful. Refresh token deleted.");
    }
}
