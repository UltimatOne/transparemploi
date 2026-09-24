package com.transparemploi.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleUpdateRequest {

    @NotBlank(message = "Le rôle est obligatoire")
    private String role; // "USER" ou "ADMIN"
}
