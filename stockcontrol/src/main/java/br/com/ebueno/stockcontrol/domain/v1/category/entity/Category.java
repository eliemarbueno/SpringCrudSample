/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.domain.v1.category.entity;

import java.util.UUID;

import br.com.ebueno.stockcontrol.domain.v1.interfaces.IIdAsUUID;
import br.com.ebueno.stockcontrol.domain.v1.interfaces.IName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "categories")
@Entity
public class Category implements IIdAsUUID, IName {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "category_id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "category_code", nullable = false, length = 50)
    private String code;
    @Column(name = "category_name", nullable = false, length = 255)
    private String name;
    @Column(name = "category_description", length = 765)
    private String description;
    @Column(name = "category_image_url", length = 765)
    private String imageUrl;
}
