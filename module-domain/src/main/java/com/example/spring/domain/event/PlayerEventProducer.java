package com.example.spring.domain.event;

import java.util.List;

public interface PlayerEventProducer {
    void push(DomainEvent event);

    void pushAll(List<DomainEvent> events);
}
