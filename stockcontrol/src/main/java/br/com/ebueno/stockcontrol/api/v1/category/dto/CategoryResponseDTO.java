package br.com.ebueno.stockcontrol.api.v1.category.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CategoryResponseDTO {
	private UUID id;
	private String code;
	private String name;
	private String description;
	private String imageUrl;
}
