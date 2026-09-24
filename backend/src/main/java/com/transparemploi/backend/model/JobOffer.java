package com.transparemploi.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Représente une offre d'emploi publiée sur une plateforme.
 * Pensée pour évoluer (tags, salaires, transparence détaillée, etc.).
 */
@Entity
@Table(name = "job_offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobOffer {

    /**
     * Identifiant technique unique de l'offre.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Intitulé du poste.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Nom de l'entreprise.
     */
    @Column(nullable = false)
    private String company;

    /**
     * Localisation principale du poste.
     */
    @Column(nullable = false)
    private String location;

    /**
     * Description textuelle de l'offre.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Indique si l'offre respecte les critères de transparence.
     */
    @Column(nullable = false)
    private boolean transparent;
}
