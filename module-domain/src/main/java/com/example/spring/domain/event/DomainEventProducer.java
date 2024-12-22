package com.example.spring.domain.event;

import java.util.List;

public interface DomainEventProducer {
    void send(DomainEvent event) throws Exception;

    void sendBatch(List<DomainEvent> events) throws Exception;
}
