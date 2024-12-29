package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;

import java.util.List;

public interface TeamMessageProducer {
    void send(DomainEvent event);

    void sendBatch(List<DomainEvent> events);
}
