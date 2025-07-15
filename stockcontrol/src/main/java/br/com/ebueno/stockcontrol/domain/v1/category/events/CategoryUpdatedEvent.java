package br.com.ebueno.stockcontrol.domain.v1.category.events;

import br.com.ebueno.stockcontrol.domain.v1.category.entity.Category;
import br.com.ebueno.stockcontrol.domain.v1.events.DomainEvent;

public class CategoryUpdatedEvent extends DomainEvent<Category> {

	public CategoryUpdatedEvent(String eventType, String aggregateId, Category data) {
		super(eventType, aggregateId, data);
	}
}
