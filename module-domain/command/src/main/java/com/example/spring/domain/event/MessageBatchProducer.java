package com.example.spring.domain.event;

import com.example.spring.domain.event.model.DomainEvent;

import java.util.List;

public interface MessageBatchProducer {
    void sendBatch(List<DomainEvent> events);
}
