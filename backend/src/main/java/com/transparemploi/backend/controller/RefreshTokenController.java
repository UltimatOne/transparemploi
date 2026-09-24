package com.transparemploi.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.AuthResponseDTO;
import com.transparemploi.backend.dto.RefreshTokenRequestDTO;
import com.transparemploi.backend.model.RefreshToken;
import com.transparemploi.backend.security.JwtService;
import com.transparemploi.backend.service.RefreshTokenService;
import com.transparemploi.backend.service.UserService;

@RestController
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserService userService;

    public RefreshTokenController(
            RefreshTokenService refreshTokenService,
            JwtService jwtService,
            UserService userService
    ) {
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO request) {

        // Validate refresh token
        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());

        // Generate new JWT
        String newJwt = jwtService.generateToken(refreshToken.getUserEmail());

        // Retrieve user role
        String role = userService.findByEmailOrThrow(refreshToken.getUserEmail()).getRole();

        // Build response
        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(newJwt);
        response.setRefreshToken(refreshToken.getToken());
        response.setRefreshTokenExpiry(refreshToken.getExpiryDate().toEpochMilli());
        response.setRole(role);

        return ResponseEntity.ok(response);
    }
}
