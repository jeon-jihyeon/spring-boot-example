package com.example.spring.domain.event;

import java.util.List;

public interface MessageBatchProducer {
    void sendBatch(List<DomainEvent> events);
}
