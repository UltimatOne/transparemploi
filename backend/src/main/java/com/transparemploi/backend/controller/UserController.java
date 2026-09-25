package com.transparemploi.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.RoleUpdateRequest;
import com.transparemploi.backend.dto.UserResponse;
import com.transparemploi.backend.dto.UserUpdateRequest;
import com.transparemploi.backend.exception.ForbiddenException;
import com.transparemploi.backend.mapper.UserMapper;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // ---------------------------
    // GET ALL USERS (ADMIN ONLY)
    // ---------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    // ---------------------------
    // GET USER BY ID
    // ---------------------------
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!"ADMIN".equals(current.getRole()) && !current.getId().equals(id)) {
            throw new ForbiddenException("Access denied");
        }

        User user = userService.findByIdOrThrow(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    // ---------------------------
    // UPDATE USER
    // ---------------------------
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!"ADMIN".equals(current.getRole()) && !current.getId().equals(id)) {
            throw new ForbiddenException("Access denied");
        }

        User updated = userService.update(id, request);
        return ResponseEntity.ok(userMapper.toResponse(updated));
    }

    // ---------------------------
    // UPDATE USER ROLE (ADMIN ONLY)
    // ---------------------------
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleUpdateRequest request
    ) {
        User updated = userService.updateRole(id, request.getRole());
        return ResponseEntity.ok(userMapper.toResponse(updated));
    }

    // ---------------------------
    // DELETE USER (ADMIN ONLY)
    // ---------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        User deleted = userService.delete(id);
        return ResponseEntity.ok(userMapper.toResponse(deleted));
    }
}
