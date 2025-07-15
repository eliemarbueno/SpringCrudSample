/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.api.v1.category.controllers;

import java.util.UUID;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryCreateDTO;
import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryResponseDTO;
import br.com.ebueno.stockcontrol.api.v1.category.dto.CategoryUpdateDTO;
import br.com.ebueno.stockcontrol.common.v1.base.controllers.AbstractCrudWithNameControllerTest;
import br.com.ebueno.stockcontrol.common.v1.constants.ConstantsApiEndpoints;
import br.com.ebueno.stockcontrol.domain.v1.category.entity.Category;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerTest extends AbstractCrudWithNameControllerTest<Category, CategoryCreateDTO, CategoryUpdateDTO, CategoryResponseDTO, UUID> {

    public CategoryControllerTest() {
        this.baseUrl = ConstantsApiEndpoints.CATEGORY_V1;
        this.filterNameContaining = "ategory";
	    this.filterNameContainingNotFound = "qwertyuiopasdfghjklçzxcvbnm";
	    this.filterNameEquals = "Category 1";
	    this.filterNameEqualsNotFound = "qwertyuiopasdfghjklçzxcvbnm";

        this.hasUniqueItem = false;
	    this.uniqueItemIsCreated = false;
	    this.pageSize = 2;
	    this.pageNumber = 0;
	    this.sortBy = "id";
	    this.sortDirection = "ASC";
        this.createDTOUnique = new CategoryCreateDTO("Unique Category", "This is a unique category for testing",
                "http://files.ebuenosoftware.com/test/category.webp");

        this.createDTOBefore = new CategoryCreateDTO("Before Category", "This is a category created before the unique one",
                "http://files.ebuenosoftware.com/test/category_before.webp");

        this.createDTO = new CategoryCreateDTO("Category", "This is a category for testing",
                "http://files.ebuenosoftware.com/test/category.webp");
               
        this.updateDTO = new CategoryUpdateDTO("Updated Category", "This is an updated category for testing",
                "http://files.ebuenosoftware.com/test/category_updated.webp");
    }


}
