package com.example.spring.domain.event;

public interface PlayerEventConsumer {
    void pull(DomainEvent event);
}
