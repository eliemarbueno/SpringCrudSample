package br.com.ebueno.stockcontrol.domain.v1.category.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryCreateDTO;
import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryResponseDTO;
import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryUpdateDTO;
import br.com.ebueno.stockcontrol.common.v1.base.service.AbstractCrudWithNameService;
import br.com.ebueno.stockcontrol.common.v1.util.MappingService;
import br.com.ebueno.stockcontrol.domain.v1.category.entity.Category;
import br.com.ebueno.stockcontrol.domain.v1.category.repository.CategoryRepository;

@Service
public class CategoryService
		extends AbstractCrudWithNameService<Category, CategoryCreateDTO, CategoryUpdateDTO, CategoryResponseDTO, UUID> {
	private final CategoryRepository repository;

	/**
	 * Constructor for CategoryService.
	 *
	 * @param categoryRepository the repository for category entities
	 * @param eventPublisher     the event publisher for application events
	 * @param mappingService     the service for mapping between entities and DTOs
	 */
	public CategoryService(CategoryRepository categoryRepository, ApplicationEventPublisher eventPublisher,
			MappingService mappingService) {
		super(categoryRepository, eventPublisher, mappingService, Category.class, CategoryResponseDTO.class);
		this.repository = categoryRepository;
	}

	@Override
	protected void setBeforeCreate(Category entity) {
		//this field only used to how this feature / sample
		long count = repository.count();
		String code = "CATEGORY-" + (++count);
		while (repository.existsByCode(code)) {
			code = "CATEGORY-" + (++count);
		}
		entity.setCode(code);
	}
}
