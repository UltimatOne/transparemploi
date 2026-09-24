package com.transparemploi.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.JobOfferRequest;
import com.transparemploi.backend.dto.JobOfferResponse;
import com.transparemploi.backend.model.JobOffer;
import com.transparemploi.backend.service.JobOfferService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class JobOfferController {

    private final JobOfferService jobOfferService;

    // ---------------------------
    // GET ALL
    // ---------------------------
    @GetMapping
    public ResponseEntity<List<JobOfferResponse>> getAll() {
        List<JobOfferResponse> offers = jobOfferService.getAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(offers);
    }

    // ---------------------------
    // CREATE
    // ---------------------------
    @PostMapping
    public ResponseEntity<JobOfferResponse> create(@Valid @RequestBody JobOfferRequest request) {

        JobOffer offer = JobOffer.builder()
                .title(request.getTitle())
                .company(request.getCompany())
                .location(request.getLocation())
                .description(request.getDescription())
                .transparent(request.isTransparent())
                .build();

        JobOffer created = jobOfferService.save(offer);

        return ResponseEntity.ok(toResponse(created));
    }

    // ---------------------------
    // GET TRANSPARENT OFFERS
    // ---------------------------
    @GetMapping("/transparent")
    public ResponseEntity<List<JobOfferResponse>> getTransparent() {
        List<JobOfferResponse> offers = jobOfferService.getTransparentOffers()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(offers);
    }

    // ---------------------------
    // MAPPER
    // ---------------------------
    private JobOfferResponse toResponse(JobOffer offer) {
        JobOfferResponse res = new JobOfferResponse();
        res.setId(offer.getId());
        res.setTitle(offer.getTitle());
        res.setCompany(offer.getCompany());
        res.setLocation(offer.getLocation());
        res.setDescription(offer.getDescription());
        res.setTransparent(offer.isTransparent());
        return res;
    }
}
