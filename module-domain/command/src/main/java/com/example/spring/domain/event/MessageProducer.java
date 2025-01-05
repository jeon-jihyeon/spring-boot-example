package com.example.spring.domain.event;

import com.example.spring.domain.event.model.DomainEvent;

public interface MessageProducer {
    void send(DomainEvent event);
}
