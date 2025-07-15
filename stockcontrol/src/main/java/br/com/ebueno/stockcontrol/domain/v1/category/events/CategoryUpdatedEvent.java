/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.domain.v1.category.events;

import br.com.ebueno.stockcontrol.domain.v1.category.entity.Category;
import br.com.ebueno.stockcontrol.domain.v1.events.DomainEvent;

public class CategoryUpdatedEvent extends DomainEvent<Category> {

	public CategoryUpdatedEvent(String eventType, String aggregateId, Category data) {
		super(eventType, aggregateId, data);
	}
}
