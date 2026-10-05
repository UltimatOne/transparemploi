package com.transparemploi.backend.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.transparemploi.backend.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long ACCESS_TOKEN_EXPIRATION_MS = 24 * 60 * 60 * 1000; // 24h
    private static final long REFRESH_TOKEN_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000; // 7 jours

    // ---------------------------
    // SIGNING KEY
    // ---------------------------
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ---------------------------
    // GENERATE ACCESS TOKEN
    // ---------------------------
    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_MS);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ---------------------------
    // GENERATE REFRESH TOKEN
    // ---------------------------
    public String generateRefreshToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        // Pas de rôle dans le refresh token

        Date now = new Date();
        Date expiry = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_MS);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ---------------------------
    // EXTRACT EMAIL
    // ---------------------------
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ---------------------------
    // EXTRACT ALL CLAIMS
    // ---------------------------
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ---------------------------
    // VALIDATE ACCESS TOKEN
    // ---------------------------
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            System.out.println("JWT ERROR → " + e.getMessage());
            return false;
        }
    }

    // ---------------------------
    // VALIDATE REFRESH TOKEN
    // ---------------------------
    public boolean isRefreshTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            System.out.println("REFRESH JWT ERROR → " + e.getMessage());
            return false;
        }
    }

    // ---------------------------
    // REFRESH TOKEN EXPIRY (SECONDS)
    // ---------------------------
    public long getRefreshTokenExpirySeconds() {
        return REFRESH_TOKEN_EXPIRATION_MS / 1000;
    }
}
