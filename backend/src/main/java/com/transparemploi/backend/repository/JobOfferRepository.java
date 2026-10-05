package com.transparemploi.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.transparemploi.backend.model.JobOffer;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    /**
     * Retourne les offres filtrées par transparence.
     */
    List<JobOffer> findByTransparent(boolean transparent);

    /**
     * Recherche une offre par mot-clé (titre, entreprise, localisation,
     * description).
     */
    @Query("""
        SELECT o FROM JobOffer o
        WHERE LOWER(o.title) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(o.company) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(o.location) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(o.description) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<JobOffer> search(String query);
}
