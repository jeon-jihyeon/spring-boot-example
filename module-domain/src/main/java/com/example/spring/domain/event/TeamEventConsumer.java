package com.example.spring.domain.event;

public interface TeamEventConsumer {
    void pull(DomainEvent event);
}
