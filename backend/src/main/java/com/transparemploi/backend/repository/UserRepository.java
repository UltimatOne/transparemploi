package com.transparemploi.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transparemploi.backend.model.User;

/**
 * Accès aux données pour les utilisateurs.
 * Utilise Spring Data JPA pour la scalabilité et la maintenabilité.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie l'existence d'un utilisateur par email.
     */
    boolean existsByEmail(String email);
}
