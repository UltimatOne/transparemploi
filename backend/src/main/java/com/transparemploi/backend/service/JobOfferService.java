package com.transparemploi.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transparemploi.backend.model.JobOffer;
import com.transparemploi.backend.repository.JobOfferRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service métier pour la gestion des offres d'emploi.
 * Conçu pour supporter des règles métier plus complexes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;

    // ----------------------------------------------------
    // GET ALL OFFERS
    // ----------------------------------------------------
    public List<JobOffer> getAll() {
        log.info("📄 Fetching all job offers");
        return jobOfferRepository.findAll();
    }

    // ----------------------------------------------------
    // FIND BY ID (utile pour le futur CRUD)
    // ----------------------------------------------------
    public JobOffer findByIdOrThrow(Long id) {
        log.info("🔍 Searching job offer by ID: {}", id);

        return jobOfferRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("❌ Job offer not found with ID: {}", id);
                    return new IllegalArgumentException("Offre introuvable");
                });
    }

    // ----------------------------------------------------
    // CREATE OR UPDATE OFFER
    // ----------------------------------------------------
    @Transactional
    public JobOffer save(JobOffer offer) {

        log.info("✏️ Saving job offer (title={}, company={}, transparent={})",
                offer.getTitle(), offer.getCompany(), offer.isTransparent());

        // Validation métier minimale
        if (offer.getTitle() == null || offer.getTitle().isBlank()) {
            log.error("❌ Job offer title is missing");
            throw new IllegalArgumentException("Le titre est obligatoire");
        }

        if (offer.getCompany() == null || offer.getCompany().isBlank()) {
            log.error("❌ Job offer company is missing");
            throw new IllegalArgumentException("Le nom de l'entreprise est obligatoire");
        }

        if (offer.getLocation() == null || offer.getLocation().isBlank()) {
            log.error("❌ Job offer location is missing");
            throw new IllegalArgumentException("La localisation est obligatoire");
        }

        if (offer.getDescription() == null || offer.getDescription().isBlank()) {
            log.error("❌ Job offer description is missing");
            throw new IllegalArgumentException("La description est obligatoire");
        }

        JobOffer saved = jobOfferRepository.save(offer);

        log.info("🟢 Job offer saved successfully with ID: {}", saved.getId());
        return saved;
    }

    // ----------------------------------------------------
    // GET TRANSPARENT OFFERS
    // ----------------------------------------------------
    public List<JobOffer> getTransparentOffers() {
        log.info("🔍 Fetching transparent job offers");
        return jobOfferRepository.findByTransparent(true);
    }
}
