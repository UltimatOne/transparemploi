package com.transparemploi.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // ----------------------------------------------------
    // GET ALL USERS (ADMIN ONLY)
    // ----------------------------------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        log.info("📄 USERS → Fetching all users");

        List<UserResponse> users = userService.getAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    // ----------------------------------------------------
    // GET USER BY ID
    // ----------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id,
            Authentication authentication
    ) {

        String email = authentication.getName();
        log.info("🔍 USERS → getUserById called by {}", email);

        User current = userService.findByEmailOrThrow(email);

        if (!"ADMIN".equals(current.getRole()) && !current.getId().equals(id)) {
            log.warn("⛔ USERS → Forbidden access by {}", email);
            throw new ForbiddenException("Access denied");
        }

        User user = userService.findByIdOrThrow(id);

        log.info("🟩 USERS → User {} fetched successfully", id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    // ----------------------------------------------------
    // UPDATE USER
    // ----------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();
        log.info("✏️ USERS → updateUser called by {}", email);

        User current = userService.findByEmailOrThrow(email);

        if (!"ADMIN".equals(current.getRole()) && !current.getId().equals(id)) {
            log.warn("⛔ USERS → Forbidden update attempt by {}", email);
            throw new ForbiddenException("Access denied");
        }

        User updated = userService.update(id, request);

        log.info("🟩 USERS → User {} updated successfully", id);
        return ResponseEntity.ok(userMapper.toResponse(updated));
    }

    // ----------------------------------------------------
    // UPDATE USER ROLE (ADMIN ONLY)
    // ----------------------------------------------------
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleUpdateRequest request
    ) {

        log.info("🔧 USERS → updateRole called for user {}", id);

        User updated = userService.updateRole(id, request.getRole());

        log.info("🟩 USERS → Role updated successfully for user {}", id);
        return ResponseEntity.ok(userMapper.toResponse(updated));
    }

    // ----------------------------------------------------
    // DELETE USER (ADMIN ONLY)
    // ----------------------------------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {

        log.info("🗑️ USERS → deleteUser called for user {}", id);

        User deleted = userService.delete(id);

        log.info("🟩 USERS → User {} deleted successfully", id);
        return ResponseEntity.ok(userMapper.toResponse(deleted));
    }
}
