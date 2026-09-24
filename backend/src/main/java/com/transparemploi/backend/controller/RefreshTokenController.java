package com.transparemploi.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.RefreshTokenRequestDTO;
import com.transparemploi.backend.dto.AuthResponseDTO;
import com.transparemploi.backend.model.RefreshToken;
import com.transparemploi.backend.service.RefreshTokenService;
import com.transparemploi.backend.security.JwtService;

@RestController
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public RefreshTokenController(RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO request) {

        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());

        String newJwt = jwtService.generateToken(refreshToken.getUserEmail());

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(newJwt);

        return ResponseEntity.ok(response);
    }
}
