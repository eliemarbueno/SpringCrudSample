package br.com.ebueno.stockcontrol.api.v1.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateDTO(
        @NotBlank String name,
        String description,
        String imageUrl
){}
