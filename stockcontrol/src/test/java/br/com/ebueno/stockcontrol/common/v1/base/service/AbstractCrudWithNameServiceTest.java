/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.common.v1.base.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.ebueno.stockcontrol.common.v1.base.repository.BaseWithNameRepository;
import br.com.ebueno.stockcontrol.domain.v1.interfaces.IIdAsUUID;
import br.com.ebueno.stockcontrol.domain.v1.interfaces.IName;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebClient
@ActiveProfiles("test")
public abstract class AbstractCrudWithNameServiceTest <E, CreateDTO, UpdateDTO, ResponseDTO, ID> 
extends AbstractCrudServiceTest<E, CreateDTO, UpdateDTO, ResponseDTO, ID> {

	@Autowired
	protected AbstractCrudWithNameService<E, CreateDTO, UpdateDTO, ResponseDTO, ID> service;
	@Autowired
	protected BaseWithNameRepository<E, ID> repository;

	@DisplayName("Should find by name with success and with valid data provided")
	@Test
	void findByName_shouldReturnDto_whenUniqueNameExists() {
		try {

			//if only one item by name
			if (hasUniqueItem) {
				var item = service.findByName(((IName) responseCreateUniqueDTO).getName());
				assertNotNull(item, "Response list should not be null when finding by name");
				if (item instanceof IName) {
					assertEquals(item, responseCreateUniqueDTO, "Item should match the created unique item");
					assertEquals(((IName) item).getName(), ((IName) responseCreateUniqueDTO).getName(), "Item Name should match the created unique item name");
				} else {
					fail("Item ResponseDTO does not implement IName");
				}
				
			} else {
				assertFalse(hasUniqueItem, "Unique item is required to validate this test");				
			}
		} catch (Exception e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}

	@DisplayName("Should validate find by name with empty name")
	@Test
	void findByName_shouldReturnPagedResults_whenNameIsEmpty() {
		try {
			var response = service.findByName("", pageable); // This actually calls findAllByNameContainingIgnoreCase
			assertNotNull(response, "Response should not be null when finding by empty name");
			assertTrue(!response.isEmpty(), "Response should not be empty when finding by empty name");
		} catch (IllegalArgumentException e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}
	
	@DisplayName("Should validate find by name with null name")
	@Test
	void findByName_shouldReturnPagedResults_whenNameIsNull() {
		try {
			var response = service.findByName(null, pageable);
			assertNotNull(response, "Response should not be null when finding by null name");
			assertTrue(!response.isEmpty(), "Response should not be empty when finding by null name");
		} catch (IllegalArgumentException e) {
			fail("Validation should not throw an exception with valid data: " + e.getMessage());
		}
	}

}