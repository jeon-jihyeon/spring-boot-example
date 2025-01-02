package com.example.spring.domain.event;

public interface DomainEventOutbox {
    void save(DomainEvent event);
}
