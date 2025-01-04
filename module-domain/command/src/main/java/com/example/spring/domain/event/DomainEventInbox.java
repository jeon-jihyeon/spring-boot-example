package com.example.spring.domain.event;

public interface DomainEventInbox {
    void save(DomainEvent event);

    boolean exists(Long id);
}
