package com.example.spring.domain.event;

import java.util.List;

public interface CommandMessageProducer {
    void send(DomainEvent event);

    void sendBatch(List<DomainEvent> events);
}
