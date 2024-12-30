package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;

import java.util.List;

public interface PlayerMessageProducer {
    void send(DomainEvent event);

    void sendBatch(List<DomainEvent> events);
}
