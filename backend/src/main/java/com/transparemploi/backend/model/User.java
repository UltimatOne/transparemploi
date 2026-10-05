package com.transparemploi.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Représente un utilisateur de la plateforme.
 * Conçue pour être extensible (rôles, profils, etc.).
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    /**
     * Identifiant technique unique de l'utilisateur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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
