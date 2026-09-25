package com.transparemploi.backend.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transparemploi.backend.model.RefreshToken;
import com.transparemploi.backend.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken createRefreshToken(String userEmail) {

        // Supprimer les anciens tokens de cet utilisateur
        refreshTokenRepository.deleteByUserEmail(userEmail);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUserEmail(userEmail);

        // Expiration dans 7 jours
        refreshToken.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60));

        return refreshTokenRepository.save(refreshToken);
    }

    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    public RefreshToken validateRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token invalide"));

        if (isExpired(refreshToken)) {
            refreshTokenRepository.delete(refreshToken);
            throw new IllegalArgumentException("Refresh token expiré");
        }

        return refreshToken;
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteByUserEmail(email);
    }

}
