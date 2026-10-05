package com.transparemploi.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OfferReportRequest {

    @NotBlank(message = "L'URL est obligatoire")
    private String url;

    private String commentaire;
}
