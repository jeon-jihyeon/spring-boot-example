package com.example.spring.domain.event;

import org.springframework.stereotype.Component;

@Component
public class QueryInboxEventHandler {
    private final DomainEventInbox inbox;
    private final DomainEventOutbox outbox;

    public QueryInboxEventHandler(DomainEventInbox inbox, DomainEventOutbox outbox) {
        this.inbox = inbox;
        this.outbox = outbox;
    }

    public void handle(DomainEvent event) {
        if (inbox.exists(event.id())) throw new EventAlreadyExistsException();
        outbox.save(event.complete());
    }
}
