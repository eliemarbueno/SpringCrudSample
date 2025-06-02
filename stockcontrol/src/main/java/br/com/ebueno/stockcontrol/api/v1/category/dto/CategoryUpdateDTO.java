package br.com.ebueno.stockcontrol.api.v1.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateDTO(
        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String description
) { }