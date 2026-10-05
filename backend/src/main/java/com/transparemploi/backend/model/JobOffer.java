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
 * Représente une offre d'emploi publiée sur une plateforme.
 * Pensée pour évoluer (tags, salaires, transparence détaillée, etc.).
 */
@Entity
@Table(name = "job_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JobOffer {

    /**
     * Identifiant technique unique de l'offre.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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
