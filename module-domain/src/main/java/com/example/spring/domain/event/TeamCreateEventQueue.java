package com.example.spring.domain.event;

public interface TeamCreateEventQueue {
    void pull(DomainEvent event);
}
