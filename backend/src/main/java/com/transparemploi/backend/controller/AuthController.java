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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    // ----------------------------------------------------
    // REGISTER
    // ----------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {

        log.info("🟦 AUTH → register() called for email={}", request.getEmail());

        User created = authService.register(request);

        log.info("🟩 AUTH → User registered with id={}", created.getId());
        return ResponseEntity.ok(created);
    }

    // ----------------------------------------------------
    // LOGIN
    // ----------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        log.info("🟦 AUTH → login() called for email={}", request.getEmail());

        AuthResponseDTO auth = authService.login(request);

        // Cookie httpOnly contenant le refresh token
        ResponseCookie cookie = ResponseCookie.from("refreshToken", auth.getRefreshToken())
                .httpOnly(true)
                .secure(false) // mettre true en production
                .sameSite("Strict")
                .path("/api/auth/refresh")
                .maxAge(auth.getRefreshTokenExpiry())
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        log.info("🟩 AUTH → Login successful, cookie set");
        return ResponseEntity.ok(auth);
    }

    // ----------------------------------------------------
    // REFRESH TOKEN
    // ----------------------------------------------------
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {

        log.info("🟦 AUTH → refresh() called");

        AuthResponseDTO auth = authService.refresh(refreshToken);

        // Nouveau cookie httpOnly
        ResponseCookie cookie = ResponseCookie.from("refreshToken", auth.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/api/auth/refresh")
                .maxAge(auth.getRefreshTokenExpiry())
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        log.info("🟩 AUTH → Refresh successful, new cookie set");
        return ResponseEntity.ok(auth);
    }
}
