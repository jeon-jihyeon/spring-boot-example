package com.example.spring.domain.outbox;

public interface OutboxMessageProducer {
    void send(OutboxEvent event);
}
