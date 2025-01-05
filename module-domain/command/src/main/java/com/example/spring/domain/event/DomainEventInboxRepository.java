package com.example.spring.domain.event;

import com.example.spring.domain.event.model.DomainEvent;

public interface DomainEventInboxRepository {
    void save(DomainEvent event);

    boolean exists(Long id);
}
