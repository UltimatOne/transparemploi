package com.transparemploi.backend.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.service.RefreshTokenService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class LogoutController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication, HttpServletResponse response) {

        String email = authentication.getName(); // email extrait du JWT

        log.info("🚪 LOGOUT → User {} requested logout", email);

        refreshTokenService.deleteRefreshToken(email);

        log.info("🗑️ LOGOUT → Refresh token deleted for user {}", email);

        // Suppression du cookie côté client
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false) // mettre true en production
                .sameSite("Strict")
                .path("/api/auth/refresh")
                .maxAge(0) // suppression immédiate
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        log.info("🟩 LOGOUT → Cookie cleared");

        return ResponseEntity.ok("Logout successful. Refresh token deleted.");
    }
}
