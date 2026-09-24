package com.transparemploi.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobOfferRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotBlank(message = "Le nom de l'entreprise est obligatoire")
    private String company;

    @NotBlank(message = "La localisation est obligatoire")
    private String location;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    private boolean transparent;
}
