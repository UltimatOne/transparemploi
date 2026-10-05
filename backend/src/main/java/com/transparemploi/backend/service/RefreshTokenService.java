package com.transparemploi.backend.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transparemploi.backend.model.RefreshToken;
import com.transparemploi.backend.repository.RefreshTokenRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // ----------------------------------------------------
    // CREATE REFRESH TOKEN
    // ----------------------------------------------------
    @Transactional
    public RefreshToken createRefreshToken(String userEmail) {

        log.info("🔄 Creating new refresh token for user: {}", userEmail);

        // Supprimer les anciens tokens de cet utilisateur
        refreshTokenRepository.deleteByUserEmail(userEmail);
        log.info("🧹 Old refresh tokens deleted for user: {}", userEmail);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUserEmail(userEmail);

        // Expiration dans 7 jours
        refreshToken.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60));

        RefreshToken saved = refreshTokenRepository.save(refreshToken);

        log.info("🟢 New refresh token created: {}", saved.getToken());

        return saved;
    }

    // ----------------------------------------------------
    // CHECK EXPIRATION
    // ----------------------------------------------------
    public boolean isExpired(RefreshToken token) {
        boolean expired = token.getExpiryDate().isBefore(Instant.now());
        log.debug("⏳ Checking expiration for token {} → expired = {}", token.getToken(), expired);
        return expired;
    }

    // ----------------------------------------------------
    // VALIDATE REFRESH TOKEN
    // ----------------------------------------------------
    public RefreshToken validateRefreshToken(String token) {

        log.info("🔍 Validating refresh token: {}", token);

        if (token == null || token.isBlank()) {
            log.error("❌ Refresh token is null or blank");
            throw new IllegalArgumentException("Refresh token manquant");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.error("❌ Refresh token not found in database");
                    return new IllegalArgumentException("Refresh token invalide");
                });

        if (isExpired(refreshToken)) {
            log.warn("⚠️ Refresh token expired, deleting it: {}", refreshToken.getToken());
            refreshTokenRepository.delete(refreshToken);
            throw new IllegalArgumentException("Refresh token expiré");
        }

        log.info("🟢 Refresh token valid for user: {}", refreshToken.getUserEmail());
        return refreshToken;
    }

    // ----------------------------------------------------
    // DELETE TOKEN FOR USER
    // ----------------------------------------------------
    public void deleteRefreshToken(String email) {
        log.info("🗑️ Deleting refresh token(s) for user: {}", email);
        refreshTokenRepository.deleteByUserEmail(email);
    }

    // ----------------------------------------------------
    // OPTIONAL: ROTATE TOKEN (cleaner refresh flow)
    // ----------------------------------------------------
    @Transactional
    public RefreshToken rotateRefreshToken(String userEmail) {
        log.info("🔄 Rotating refresh token for user: {}", userEmail);
        return createRefreshToken(userEmail);
    }
}
