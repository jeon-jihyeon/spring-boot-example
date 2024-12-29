package com.example.spring.domain.event;

import com.example.spring.domain.event.dto.InboxCompleteEvent;
import org.springframework.stereotype.Component;

@Component
public class QueryInboxService {
    private final DomainEventInbox inbox;
    private final DomainEventOutbox outbox;

    public QueryInboxService(DomainEventInbox inbox, DomainEventOutbox outbox) {
        this.inbox = inbox;
        this.outbox = outbox;
    }

    public void receive(DomainEvent event) {
        if (inbox.exists(event.id())) throw new EventAlreadyExistsException();
        outbox.save(event.complete());
    }

    public void create(DomainEvent event) {
        inbox.save(event.copy());
    }

    public void complete(InboxCompleteEvent event) {
        final DomainEvent domainEvent = inbox.findEvent(event.id());
        inbox.save(domainEvent.complete());
    }
}
