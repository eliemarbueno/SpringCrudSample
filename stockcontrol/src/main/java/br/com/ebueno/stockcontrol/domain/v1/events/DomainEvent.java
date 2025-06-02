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
