/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.common.v1.base.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E, ID> extends JpaRepository<E, ID> {
	Page<E> findAll(Pageable pageable);
    List<E> findAll();   
}
