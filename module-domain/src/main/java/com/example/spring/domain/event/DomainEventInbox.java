package com.example.spring.domain.event;

public interface DomainEventInbox {
    void save(DomainEvent event);

    DomainEvent findEvent(Long id);

    boolean exists(Long id);
}
