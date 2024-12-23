package com.example.spring.domain.event;

public interface TeamCreateEventConsumer {
    void pull(DomainEvent event);
}
