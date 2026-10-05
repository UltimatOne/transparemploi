package com.transparemploi.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.UserResponse;
import com.transparemploi.backend.mapper.UserMapper;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class MeController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {

        log.info("👤 ME → Request received");

        // Le principal est l'email, pas un User
        String email = authentication.getName();

        log.info("🔍 ME → Extracted email from JWT: {}", email);

        User user = userService.findByEmailOrThrow(email);

        log.info("🟩 ME → User found: id={}, email={}", user.getId(), user.getEmail());

        return ResponseEntity.ok(userMapper.toResponse(user));
    }
}
