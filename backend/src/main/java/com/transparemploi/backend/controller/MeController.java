package com.transparemploi.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.UserResponse;
import com.transparemploi.backend.mapper.UserMapper;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.service.UserService;

@RestController
public class MeController {

    private final UserService userService;
    private final UserMapper userMapper;

    public MeController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/api/auth/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {

        String email = ((User) authentication.getPrincipal()).getEmail(); // extrait du JWT

        var user = userService.findByEmailOrThrow(email);

        return ResponseEntity.ok(userMapper.toResponse(user));
    }
}
