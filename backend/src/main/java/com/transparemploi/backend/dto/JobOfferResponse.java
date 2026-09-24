package com.transparemploi.backend.dto;

import lombok.Data;

@Data
public class JobOfferResponse {

    private Long id;
    private String title;
    private String company;
    private String location;
    private String description;
    private boolean transparent;
}
