package com.transparemploi.backend.repository;

import com.transparemploi.backend.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Accès aux données pour les offres d'emploi.
 */
@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    /**
     * Retourne les offres filtrées par transparence.
     *
     * @param transparent true pour les offres transparentes
     * @return liste des offres correspondantes
     */
    List<JobOffer> findByTransparent(boolean transparent);
}
