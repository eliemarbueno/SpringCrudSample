package br.com.ebueno.stockcontrol.api.v1.category.dto;

import java.util.UUID;

import br.com.ebueno.stockcontrol.domain.v1.interfaces.IIdAsUUID;
import br.com.ebueno.stockcontrol.domain.v1.interfaces.IName;
import lombok.Data;

@Data
public class CategoryResponseDTO implements IIdAsUUID, IName {
	private UUID id;
	private String code;
	private String name;
	private String description;
	private String imageUrl;
}
