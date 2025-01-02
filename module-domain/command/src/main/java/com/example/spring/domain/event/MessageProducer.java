package com.example.spring.domain.event;

public interface MessageProducer {
    void send(DomainEvent event);
}
