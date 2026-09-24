package com.transparemploi.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transparemploi.backend.dto.UserUpdateRequest;
import com.transparemploi.backend.model.User;
import com.transparemploi.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    // ---------------------------
    // GET ALL USERS
    // ---------------------------
    public List<User> getAll() {
        return repository.findAll();
    }

    // ---------------------------
    // FIND BY ID
    // ---------------------------
    public User findByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
    }

    // ---------------------------
    // UPDATE USER
    // ---------------------------
    public User update(Long id, UserUpdateRequest request) {

        User existing = findByIdOrThrow(id);

        // Vérifie si un autre utilisateur utilise cet email
        if (!existing.getEmail().equals(request.getEmail())
                && repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur");
        }

        existing.setEmail(request.getEmail());
        existing.setRole(request.getRole());

        // TODO : hash si tu veux permettre la modification du mot de passe
        // existing.setPassword(passwordEncoder.encode(request.getPassword()));

        return repository.save(existing);
    }

    // ---------------------------
    // DELETE USER
    // ---------------------------
    public User delete(Long id) {

        User existing = findByIdOrThrow(id);

        repository.delete(existing);

        return existing;
    }
}
