package com.example.spring.domain.event;

import org.springframework.stereotype.Component;

@Component
public class QueryNewInboxService {
    private final DomainEventInboxRepository inbox;

    public QueryNewInboxService(DomainEventInboxRepository inbox) {
        this.inbox = inbox;
    }

    public void invoke(DomainEvent outboxEvent) {
        // TODO: api 변경
        inbox.save(outboxEvent.copy());
    }
}
