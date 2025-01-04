package com.example.spring.domain.event;

public interface DomainEventInboxRepository {
    void save(DomainEvent event);

    boolean exists(Long id);
}
