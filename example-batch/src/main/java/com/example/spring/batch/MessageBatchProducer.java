package com.example.spring.batch;

import java.util.List;

public interface MessageBatchProducer {
    void sendBatch(List<DomainEvent> events);
}
