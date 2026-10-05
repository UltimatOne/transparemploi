package com.transparemploi.backend.security;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.transparemploi.backend.model.User;
import com.transparemploi.backend.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/api/auth/refresh"
    );

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        log.debug("FILTER → URI = {}", path);

        // ENDPOINTS PUBLICS
        if (PUBLIC_ENDPOINTS.contains(path)) {
            log.debug("FILTER → Public endpoint, skipping authentication");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        log.debug("FILTER → Authorization header = {}", authHeader);

        // PAS DE TOKEN → vider le contexte
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.debug("FILTER → No Bearer token, clearing context");
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        log.debug("FILTER → Token = {}", token);

        Claims claims;
        try {
            claims = jwtService.extractAllClaims(token);
        } catch (JwtException e) {
            log.warn("FILTER → Invalid JWT: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        String email = claims.getSubject();
        String roleFromToken = claims.get("role", String.class);

        log.debug("FILTER → Extracted email = {}", email);
        log.debug("FILTER → Extracted role = {}", roleFromToken);

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            log.warn("FILTER → User not found in DB");
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        // Vérification cohérence rôle JWT ↔ rôle DB
        if (!user.getRole().equals(roleFromToken)) {
            log.warn("FILTER → Role mismatch: token={}, db={}", roleFromToken, user.getRole());
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(authority)
                );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("FILTER → Authentication set in SecurityContext");

        filterChain.doFilter(request, response);
    }
}
