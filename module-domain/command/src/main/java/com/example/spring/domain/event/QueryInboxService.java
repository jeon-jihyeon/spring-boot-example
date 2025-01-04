package com.example.spring.domain.event;

import org.springframework.stereotype.Component;

@Component
public class QueryInboxService {
    private final DomainEventInboxRepository inbox;

    public QueryInboxService(DomainEventInboxRepository inbox) {
        this.inbox = inbox;
    }

    public void receive(DomainEvent event) {
        if (inbox.exists(event.id())) throw new EventAlreadyExistsException();
    }

    public void create(DomainEvent event) {
        inbox.save(event.copy());
    }

    public void complete(DomainEvent event) {
        inbox.save(event.complete());
    }
}
