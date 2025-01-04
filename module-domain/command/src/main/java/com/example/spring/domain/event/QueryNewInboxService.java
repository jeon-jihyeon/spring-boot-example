package com.example.spring.domain.event;

import org.springframework.stereotype.Component;

@Component
public class QueryNewInboxService {
    private final DomainEventInbox inbox;

    public QueryNewInboxService(DomainEventInbox inbox) {
        this.inbox = inbox;
    }

    public void invoke(DomainEvent outboxEvent) {
        inbox.save(outboxEvent.copy());
    }
}
