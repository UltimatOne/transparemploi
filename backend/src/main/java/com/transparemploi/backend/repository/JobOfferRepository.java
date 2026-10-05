package com.transparemploi.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transparemploi.backend.model.JobOffer;

/**
 * Accès aux données pour les offres d'emploi.
 */
@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    /**
     * Retourne les offres filtrées par transparence.
     */
    List<JobOffer> findByTransparent(boolean transparent);
}
