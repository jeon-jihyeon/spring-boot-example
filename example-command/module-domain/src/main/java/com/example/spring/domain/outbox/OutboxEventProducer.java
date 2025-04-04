package com.example.spring.domain.outbox;

public interface OutboxEventProducer {
    void send(OutboxEvent event);
}
