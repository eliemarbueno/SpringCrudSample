/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.common.v1.base.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.ebueno.stockcontrol.common.v1.base.service.AbstractCrudWithNameService;

public class AbstractCrudWithNameControllerTest<E, CreateDTO, UpdateDTO, ResponseDTO, ID>
		extends AbstractCrudControllerTest<E, CreateDTO, UpdateDTO, ResponseDTO, ID> {

	@Mock
	@Autowired
	protected AbstractCrudWithNameService<E, CreateDTO, UpdateDTO, ResponseDTO, ID> service;

	protected String filterNameContaining = "cat";
	protected String filterNameContainingNotFound = "qwertyuiopasdfghjklçzxcvbnm";
	protected String filterNameEquals = "Category 1";
	protected String filterNameEqualsNotFound = "qwertyuiopasdfghjklçzxcvbnm";
	
@Test
    @DisplayName("Should return paged items when GET request has no params")
    void should_ReturnPagedItems_When_GetRequestHasNoParams() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrl())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("Should return items with name containing when searching with like filter")
    void should_ReturnItemsWithNameContaining_When_SearchingWithLikeFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrl())
                .param("name", filterNameContaining)
                .param("isLike", "true")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("Should return an specific item when searching with exact name")
    void should_ReturnItemWithExactName_When_SearchingWithEqualsFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrl())
                .param("name", filterNameEquals)
                .param("isLike", "false")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("Should return all items when searching with an empty name")
    void should_ReturnAllItems_When_SearchingWithEmptyName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrl())
                .param("name", "")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }
	
	@Test
    @DisplayName("Should return all items when searching without name param")
    void should_ReturnAllItems_When_SearchingWithoutNameParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrl())
                // .param("name", null)
                .param("isLike", "true")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }


	@Test
    @DisplayName("Should return empty list when searching for a non-existent exact name")
    void should_ReturnEmptyList_When_SearchingForNonExistentExactName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrl())
                .param("name", filterNameEqualsNotFound)
                .param("isLike", "false")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());
    }

	@Test
    @DisplayName("Should return empty list when searching for a non-existent containing name")
    void should_ReturnEmptyList_When_SearchingForNonExistentContainingName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(getBaseUrl())
                .param("name", filterNameContainingNotFound)
                .param("isLike", "true")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());
    }
}
