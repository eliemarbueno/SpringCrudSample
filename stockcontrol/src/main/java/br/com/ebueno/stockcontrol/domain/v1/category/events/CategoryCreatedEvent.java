package br.com.ebueno.stockcontrol.domain.v1.category.events;

import br.com.ebueno.stockcontrol.domain.v1.category.entity.Category;
import br.com.ebueno.stockcontrol.domain.v1.events.DomainEvent;

public class CategoryCreatedEvent extends DomainEvent<Category> {

	public CategoryCreatedEvent(String eventType, String aggregateId, Category data) {
		super(eventType, aggregateId, data);
	}
}
