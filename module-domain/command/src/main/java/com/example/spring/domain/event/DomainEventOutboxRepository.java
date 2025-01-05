package com.example.spring.domain.event;

import com.example.spring.domain.event.model.DomainEvent;

import java.util.List;

public interface DomainEventOutboxRepository {
    void save(DomainEvent event);

    void createAll(List<DomainEvent> events);
}
