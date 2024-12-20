package com.example.spring.domain.event;

public interface DomainEventQueue {
    void push(DomainEvent event);

    void pull(DomainEvent event);
}
