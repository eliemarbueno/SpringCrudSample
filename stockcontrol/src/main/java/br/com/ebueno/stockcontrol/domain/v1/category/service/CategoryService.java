/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

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

/**
 * @author Eliemar Bueno
 */
@Service
public class CategoryService
		extends AbstractCrudWithNameService<Category, CategoryCreateDTO, CategoryUpdateDTO, CategoryResponseDTO, UUID> {

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
	}

	private CategoryRepository getCategoryRepository() {
		return (CategoryRepository) repository;
	}

	@Override
	protected void setBeforeCreate(Category entity) {
		//this field only used to how this feature / sample
		CategoryRepository categoryRepository = getCategoryRepository();
		long count = categoryRepository.count();
		String code = "CATEGORY-" + (++count);
		while (categoryRepository.existsByCode(code)) {
			code = "CATEGORY-" + (++count);
		}
		entity.setCode(code);
	}
}
