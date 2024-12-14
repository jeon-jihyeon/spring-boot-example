package com.example.spring.domain.event;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
