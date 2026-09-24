package com.transparemploi.backend.service;

import com.transparemploi.backend.model.JobOffer;
import com.transparemploi.backend.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour la gestion des offres d'emploi.
 * Conçu pour supporter des règles métier plus complexes.
 */
@Service
@RequiredArgsConstructor
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;

    /**
     * Retourne toutes les offres.
     *
     * @return liste des offres
     */
    public List<JobOffer> getAll() {
        return jobOfferRepository.findAll();
    }

    /**
     * Crée ou met à jour une offre.
     *
     * @param offer offre à enregistrer
     * @return offre enregistrée
     */
    public JobOffer save(JobOffer offer) {
        // TODO : ajouter validation métier (ex : champs obligatoires, transparence, etc.)
        return jobOfferRepository.save(offer);
    }

    /**
     * Retourne les offres transparentes.
     *
     * @return liste des offres transparentes
     */
    public List<JobOffer> getTransparentOffers() {
        return jobOfferRepository.findByTransparent(true);
    }
}
