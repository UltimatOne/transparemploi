package com.transparemploi.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transparemploi.backend.dto.JobOfferRequest;
import com.transparemploi.backend.dto.JobOfferResponse;
import com.transparemploi.backend.dto.OfferReportRequest;
import com.transparemploi.backend.mapper.JobOfferMapper;
import com.transparemploi.backend.model.JobOffer;
import com.transparemploi.backend.service.JobOfferService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
@Slf4j
public class JobOfferController {

    private final JobOfferService jobOfferService;
    private final JobOfferMapper jobOfferMapper;

    // ----------------------------------------------------
    // GET ALL
    // ----------------------------------------------------
    @GetMapping
    public ResponseEntity<List<JobOfferResponse>> getAll() {

        log.info("📄 Fetching all job offers");

        List<JobOfferResponse> offers = jobOfferService.getAll()
                .stream()
                .map(jobOfferMapper::toResponse)
                .toList();

        return ResponseEntity.ok(offers);
    }

    // ---------------------------
    // GET BY ID
    // ---------------------------
    @GetMapping("/{id}")
    public ResponseEntity<JobOfferResponse> getById(@PathVariable Long id) {
        JobOffer offer = jobOfferService.findByIdOrThrow(id);
        return ResponseEntity.ok(jobOfferMapper.toResponse(offer));
    }

    // ---------------------------
    // SEARCH
    // ---------------------------
    @GetMapping("/search")
    public ResponseEntity<List<JobOfferResponse>> search(@RequestParam String query) {
        List<JobOfferResponse> offers = jobOfferService.search(query)
                .stream()
                .map(jobOfferMapper::toResponse)
                .toList();

        return ResponseEntity.ok(offers);
    }

    // ---------------------------
    // CREATE
    // ----------------------------------------------------
    @PostMapping
    public ResponseEntity<JobOfferResponse> create(@Valid @RequestBody JobOfferRequest request) {

        log.info("✏️ Creating job offer: title={}, company={}, transparent={}",
                request.getTitle(), request.getCompany(), request.isTransparent());

        JobOffer offer = JobOffer.builder()
                .title(request.getTitle())
                .company(request.getCompany())
                .location(request.getLocation())
                .description(request.getDescription())
                .transparent(request.isTransparent())
                .build();

        JobOffer created = jobOfferService.save(offer);

        log.info("🟢 Job offer created with ID={}", created.getId());

        return ResponseEntity.ok(jobOfferMapper.toResponse(created));
    }

    // ----------------------------------------------------
    // GET TRANSPARENT OFFERS
    // ----------------------------------------------------
    @GetMapping("/transparent")
    public ResponseEntity<List<JobOfferResponse>> getTransparent() {

        log.info("🔍 Fetching transparent job offers");

        List<JobOfferResponse> offers = jobOfferService.getTransparentOffers()
                .stream()
                .map(jobOfferMapper::toResponse)
                .toList();

        return ResponseEntity.ok(offers);
    }

    // ---------------------------
    // REPORT
    // ---------------------------
    @PostMapping("/report")
    public ResponseEntity<Void> report(@Valid @RequestBody OfferReportRequest request) {
        jobOfferService.report(request);
        return ResponseEntity.ok().build();
    }
}
