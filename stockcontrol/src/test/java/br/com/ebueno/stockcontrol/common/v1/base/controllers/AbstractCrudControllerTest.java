/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.common.v1.base.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.ebueno.stockcontrol.common.v1.base.service.AbstractCrudService;
import br.com.ebueno.stockcontrol.common.v1.constants.ConstantsApiEndpoints;
import br.com.ebueno.stockcontrol.common.v1.util.MappingService;
import br.com.ebueno.stockcontrol.domain.v1.interfaces.IIdAsUUID;
import br.com.ebueno.stockcontrol.domain.v1.interfaces.IName;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractCrudControllerTest<E, CreateDTO, UpdateDTO, ResponseDTO, ID> {

	@Autowired
	protected MockMvc mockMvc;

	@Mock
	@Autowired
	protected AbstractCrudService<E, CreateDTO, UpdateDTO, ResponseDTO, ID> service;

	protected boolean hasUniqueItem = false;
	protected boolean uniqueItemIsCreated = false;
	protected int pageSize = 2;
	protected int pageNumber = 0;
	protected String sortBy = "id";
	protected String sortDirection = "ASC";

	protected CreateDTO createDTOUnique;
	protected CreateDTO createDTOBefore;
	protected CreateDTO createDTO;
	protected UpdateDTO updateDTO;

	protected ResponseDTO responseBeforeDTO;
	protected ResponseDTO responseCreateDTO;
	protected ResponseDTO responseCreateUniqueDTO;
	protected ResponseDTO responseUpdateDTO;
	protected ID id;
	protected Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());

	protected String baseUrl = ConstantsApiEndpoints.API_V1 + "/items";
	protected String idNotFound = "qwertyuiopasdfghjklçzxcvbnm";

	protected String getBaseUrl() {
		return baseUrl;
	}

	protected String getBaseUrlWithId() {
		return getBaseUrl() + "/" + id;
	}

	protected String getBaseUrlNotFound() {
		return getBaseUrl() + "/" + idNotFound;
	}

	
	@BeforeEach
	protected void setup() {
        if (hasUniqueItem && !uniqueItemIsCreated){
			responseCreateUniqueDTO = service.create(createDTOUnique);
			uniqueItemIsCreated = true;
		}

		responseBeforeDTO = service.create(createDTOBefore);
		if (responseBeforeDTO instanceof IIdAsUUID) {
			id = (ID) ((IIdAsUUID) responseBeforeDTO).getId();
			assertNotNull(id, "ID should not be null after creation");
		} else {
			fail("ResponseDTO does not implement IIdAsUUID");
		}
		if (responseBeforeDTO instanceof IName) {
			assertNotNull(((IName) responseBeforeDTO).getName(), "Name should not be null after creation");
		} else {
			fail("ResponseDTO does not implement IName");
		}
    }
	
	@DisplayName("Should be create a new item")
	@Test
	void should_CreateNewItem_When_PostRequestIsValid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(getBaseUrl()).contentType(MediaType.APPLICATION_JSON)
				.content(MappingService.toJson(createDTO))).andExpect(MockMvcResultMatchers.status().isCreated())
				// .andExpect(MockMvcResultMatchers.header().string("Location",
				// MockMvcResultMatchers.containsString(baseUrlWithId)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		// .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(createDTO.getName()))
		// .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(createDTO.getDescription()))
		// .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
		// .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").isNotEmpty());
		;
	}

    @DisplayName("Should be update an existing item")
    @Test
	void should_UpdateExistingItem_When_PutRequestIsValid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(getBaseUrlWithId()).contentType(MediaType.APPLICATION_JSON)
				.content(MappingService.toJson(updateDTO))).andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
				// .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updateDTO.getName()))
				// .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(updateDTO.getDescription()))
				// .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
				// .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").isNotEmpty())
                ;
	}

    @DisplayName("Should be delete an existing item")
    @Test
	void should_DeleteExistingItem_When_DeleteRequestIsValid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(getBaseUrlWithId())).andExpect(MockMvcResultMatchers.status().isNoContent());
	}
    
    @DisplayName("Should be find an existing item by ID")
    @Test
	void should_FindExistingItemById_When_GetRequestWithValidId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrlWithId())).andExpect(MockMvcResultMatchers.status().isOk());
	}

    @DisplayName("Should be find all items")
    @Test
    void should_FindAllItems_When_GetRequestIsMade() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrl())).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Should be find all items with pagination")
    @Test
    void should_FindAllItemsWithPagination_When_PagingParamsAreProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrl()).param("page", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)).param("sort", sortBy + "," + sortDirection))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}