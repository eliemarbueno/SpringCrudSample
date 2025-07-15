/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.api.v1.category.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryCreateDTO;
import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryResponseDTO;
import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryUpdateDTO;
import br.com.ebueno.stockcontrol.common.v1.constants.ConstantsApiEndpoints;
import br.com.ebueno.stockcontrol.domain.v1.category.service.CategoryService;

/**
 * @author Eliemar Bueno
 */
@RestController
@RequestMapping(ConstantsApiEndpoints.CATEGORY_V1)
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping()
	public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryCreateDTO createDTO) {
		CategoryResponseDTO dto = categoryService.create(createDTO);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponseDTO> update(@PathVariable UUID id, @RequestBody CategoryUpdateDTO updateDTO) {
		CategoryResponseDTO dto = categoryService.update(id, updateDTO);
		if (dto == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponseDTO> getById(@PathVariable UUID id) {
		CategoryResponseDTO dto = categoryService.findById(id);
		if (dto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@GetMapping()
	public ResponseEntity<Page<CategoryResponseDTO>> getAll(@RequestParam(required = false) String name,
			@RequestParam(required = false) boolean isLike, Pageable pageable) {
		if (name != null && !name.isBlank()) {
			if (isLike) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(categoryService.findAllByNameContainingIgnoreCase(name, pageable));
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllByName(name, pageable));
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(pageable));
	}
}
