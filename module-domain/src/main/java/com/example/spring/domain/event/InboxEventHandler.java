package com.example.spring.domain.event;

import org.springframework.stereotype.Component;

@Component
public class InboxEventHandler {
    private final DomainEventInbox inbox;

    public InboxEventHandler(DomainEventInbox inbox) {
        this.inbox = inbox;
    }

    public void handle(DomainEvent event) {
        if (inbox.exists(event.id())) return;
        inbox.save(event.copy());
    }
}
