package com.example.spring.domain.event;

public interface DomainEventOutboxRepository {
    void save(DomainEvent event);
}
