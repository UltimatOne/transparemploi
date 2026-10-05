package com.transparemploi.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transparemploi.backend.dto.OfferReportRequest;
import com.transparemploi.backend.model.JobOffer;
import com.transparemploi.backend.repository.JobOfferRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;

    // ---------------------------
    // GET ALL
    // ---------------------------
    public List<JobOffer> getAll() {
        log.info("📄 Fetching all job offers");
        return jobOfferRepository.findAll();
    }

    // ---------------------------
    // SAVE
    // ---------------------------
    public JobOffer save(JobOffer offer) {
        return jobOfferRepository.save(offer);
    }

    // ---------------------------
    // GET TRANSPARENT
    // ---------------------------
    public List<JobOffer> getTransparentOffers() {
        log.info("🔍 Fetching transparent job offers");
        return jobOfferRepository.findByTransparent(true);
    }

    // ---------------------------
    // GET BY ID
    // ---------------------------
    public JobOffer findByIdOrThrow(Long id) {
        return jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
    }

    // ---------------------------
    // SEARCH
    // ---------------------------
    public List<JobOffer> search(String query) {
        return jobOfferRepository.search(query);
    }

    // ---------------------------
    // REPORT
    // ---------------------------
    public void report(OfferReportRequest request) {
        // Ici tu peux :
        // - enregistrer le signalement en base
        // - envoyer un email
        // - logguer l'événement
        // - créer une entité Report si tu veux
        System.out.println("Signalement reçu : " + request.getUrl() + " | " + request.getCommentaire());
    }
}
