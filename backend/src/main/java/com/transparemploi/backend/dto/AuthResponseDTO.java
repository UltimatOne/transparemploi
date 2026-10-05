package com.transparemploi.backend.dto;

public class AuthResponseDTO {

    private String token;
    private String refreshToken;
    private long refreshTokenExpiry;
    private String role;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, String refreshToken, long refreshTokenExpiry, String role) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiry = refreshTokenExpiry;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

    public void setRefreshTokenExpiry(long refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
