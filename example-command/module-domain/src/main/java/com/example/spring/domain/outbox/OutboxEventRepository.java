package com.example.spring.domain.outbox;

public interface OutboxEventRepository {
    void save(OutboxEvent event);
}
