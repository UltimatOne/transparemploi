package com.transparemploi.backend.mapper;

import org.springframework.stereotype.Component;

import com.transparemploi.backend.dto.JobOfferResponse;
import com.transparemploi.backend.model.JobOffer;

@Component
public class JobOfferMapper {

    public JobOfferResponse toResponse(JobOffer offer) {
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
