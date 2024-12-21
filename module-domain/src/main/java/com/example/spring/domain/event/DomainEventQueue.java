package com.example.spring.domain.event;

import java.util.List;

public interface DomainEventQueue {
    void push(DomainEvent event);

    void pull(DomainEvent event);

    void pushAll(List<DomainEvent> events);
}
