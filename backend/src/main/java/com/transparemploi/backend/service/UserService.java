package com.transparemploi.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transparemploi.backend.dto.UserUpdateRequest;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;

    // ----------------------------------------------------
    // GET ALL USERS
    // ----------------------------------------------------
    public List<User> getAll() {
        log.info("📄 Fetching all users");
        return repository.findAll();
    }

    // ----------------------------------------------------
    // FIND BY ID
    // ----------------------------------------------------
    public User findByIdOrThrow(Long id) {
        log.info("🔍 Searching user by ID: {}", id);

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("❌ User not found with ID: {}", id);
                    return new IllegalArgumentException("Utilisateur introuvable");
                });
    }

    // ----------------------------------------------------
    // FIND BY EMAIL
    // ----------------------------------------------------
    public User findByEmailOrThrow(String email) {
        log.info("🔍 Searching user by email: {}", email);

        return repository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("❌ User not found with email: {}", email);
                    return new IllegalArgumentException("Utilisateur introuvable");
                });
    }

    // ----------------------------------------------------
    // UPDATE USER (email + role)
    // ----------------------------------------------------
    @Transactional
    public User update(Long id, UserUpdateRequest request) {

        log.info("✏️ Updating user {} with new email={} and role={}", 
                 id, request.getEmail(), request.getRole());

        User existing = findByIdOrThrow(id);

        // Vérifie si un autre utilisateur utilise cet email
        if (!existing.getEmail().equals(request.getEmail())
                && repository.existsByEmail(request.getEmail())) {

            log.error("❌ Email {} already used by another user", request.getEmail());
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur");
        }

        // Vérification du rôle (cohérence avec updateRole)
        if (!request.getRole().equals("ADMIN") && !request.getRole().equals("USER")) {
            log.error("❌ Invalid role provided: {}", request.getRole());
            throw new IllegalArgumentException("Rôle invalide");
        }

        existing.setEmail(request.getEmail());
        existing.setRole(request.getRole());

        User saved = repository.save(existing);

        log.info("🟢 User {} updated successfully", saved.getId());
        return saved;
    }

    // ----------------------------------------------------
    // UPDATE ROLE ONLY
    // ----------------------------------------------------
    @Transactional
    public User updateRole(Long id, String role) {

        log.info("🔧 Updating role for user {} → {}", id, role);

        User user = findByIdOrThrow(id);

        if (!role.equals("ADMIN") && !role.equals("USER")) {
            log.error("❌ Invalid role provided: {}", role);
            throw new IllegalArgumentException("Rôle invalide");
        }

        user.setRole(role);

        User saved = repository.save(user);

        log.info("🟢 Role updated successfully for user {}", saved.getId());
        return saved;
    }

    // ----------------------------------------------------
    // DELETE USER
    // ----------------------------------------------------
    @Transactional
    public User delete(Long id) {

        log.info("🗑️ Deleting user {}", id);

        User existing = findByIdOrThrow(id);

        repository.delete(existing);

        log.info("🟢 User {} deleted successfully", id);
        return existing;
    }
}
