/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.domain.v1.category.repository;

import java.util.Optional;
import java.util.UUID;

import br.com.ebueno.stockcontrol.common.v1.base.repository.BaseWithNameRepository;
import br.com.ebueno.stockcontrol.domain.v1.category.entity.Category;

public interface CategoryRepository extends BaseWithNameRepository<Category, UUID> {
    Optional<Category> findByCode(String code);
    Boolean existsByCode(String code);
}
