package com.example.spring.outbox;

import java.util.List;

public interface OutboxEventBatchProducer {
    void sendBatch(List<OutboxEvent> events);
}
