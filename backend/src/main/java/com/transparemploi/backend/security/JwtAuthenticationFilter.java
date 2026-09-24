package com.transparemploi.backend.security;

import java.io.IOException;
import java.util.List;

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
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("🔵 FILTER → URI = " + path);

        // IGNORER les endpoints publics
        if (path.startsWith("/api/auth/")) {
            System.out.println("🟢 FILTER → Ignored for public endpoint");
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println("🔵 FILTER → Authorization header = " + authHeader);

        // Pas de token → laisser passer mais vider le contexte
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            System.out.println("🟡 FILTER → No Bearer token, letting request pass");
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("🔵 FILTER → Token = " + token);

        if (!jwtService.isTokenValid(token)) {
            System.out.println("🔴 FILTER → Token INVALID");
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        // ---------------------------
        // EXTRACTION CLAIMS
        // ---------------------------
        Claims claims = jwtService.extractAllClaims(token);

        String email = claims.getSubject();
        String role = claims.get("role", String.class);

        System.out.println("🔵 FILTER → Extracted email = " + email);
        System.out.println("🔵 FILTER → Extracted role = " + role);

        User user = userRepository.findByEmail(email).orElse(null);
        System.out.println("🔵 FILTER → User found = " + user);

        if (user == null) {
            System.out.println("🔴 FILTER → User not found in DB");
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        // ---------------------------
        // CRÉATION DES AUTORITÉS
        // ---------------------------
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + role);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(authority) // CRITIQUE : authorities obligatoires
                );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("🟢 FILTER → Authentication set in SecurityContext");

        filterChain.doFilter(request, response);
        System.out.println("🟢 FILTER → Request passed to next filter");
    }
}
