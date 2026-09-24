package com.transparemploi.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.UserResponse;
import com.transparemploi.backend.dto.UserUpdateRequest;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ---------------------------
    // GET ALL USERS
    // ---------------------------
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    // ---------------------------
    // GET USER BY ID
    // ---------------------------
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.findByIdOrThrow(id);
        return ResponseEntity.ok(toResponse(user));
    }

    // ---------------------------
    // UPDATE USER
    // ---------------------------
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        User updated = userService.update(id, request);
        return ResponseEntity.ok(toResponse(updated));
    }

    // ---------------------------
    // DELETE USER
    // ---------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        User deleted = userService.delete(id);
        return ResponseEntity.ok(toResponse(deleted));
    }

    // ---------------------------
    // MAPPER
    // ---------------------------
    private UserResponse toResponse(User user) {
        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole());
        return res;
    }
}
