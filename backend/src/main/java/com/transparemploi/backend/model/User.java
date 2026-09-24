package com.transparemploi.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Représente un utilisateur de la plateforme.
 * Conçue pour être extensible (rôles, profils, etc.).
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Identifiant technique unique de l'utilisateur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Adresse email unique, utilisée comme identifiant de connexion.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Mot de passe stocké (à sécuriser : hash, salt).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Rôle fonctionnel de l'utilisateur (USER, ADMIN).
     */
    @Column(nullable = false)
    private String role;
}
