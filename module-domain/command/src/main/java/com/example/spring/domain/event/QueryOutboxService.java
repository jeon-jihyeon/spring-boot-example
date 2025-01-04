package com.example.spring.domain.event;

import org.springframework.stereotype.Component;

@Component
public class QueryOutboxService {
    private final DomainEventOutboxRepository outbox;

    public QueryOutboxService(DomainEventOutboxRepository outbox) {
        this.outbox = outbox;
    }

    public void complete(DomainEvent event) {
        outbox.save(event.complete());
    }
}
