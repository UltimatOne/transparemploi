package com.transparemploi.backend.repository;

import com.transparemploi.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Accès aux données pour les utilisateurs.
 * Utilise Spring Data JPA pour la scalabilité et la maintenabilité.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son email.
     *
     * @param email email de l'utilisateur
     * @return Optional contenant l'utilisateur si trouvé
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie l'existence d'un utilisateur par email.
     *
     * @param email email à vérifier
     * @return true si un utilisateur existe déjà
     */
    boolean existsByEmail(String email);
}
