/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.domain.v1.events;

import java.time.OffsetDateTime;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class DomainEvent<T> {
    private final String eventType;
    private final String aggregateId;
    private OffsetDateTime occurredOn;
    private T data;
    public DomainEvent(String eventType, String aggregateId, T data) {
        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.data = data;
        this.occurredOn = OffsetDateTime.now();
    }
    public String getEventType() {
        return eventType;
    }
    public String getAggregateId() {
        return aggregateId;
    }
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
    public T getData() {
        return data;
    }
}
