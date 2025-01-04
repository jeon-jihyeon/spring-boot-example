package com.example.spring.domain.event;

import java.util.List;

public interface DomainEventOutboxRepository {
    void save(DomainEvent event);

    void createAll(List<DomainEvent> events);
}
