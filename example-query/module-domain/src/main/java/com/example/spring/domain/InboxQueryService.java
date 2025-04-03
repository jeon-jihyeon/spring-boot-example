package com.example.spring.domain;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInboxRepository;
import com.example.spring.domain.event.EventAlreadyExistsException;
import org.springframework.stereotype.Component;

@Component
public class InboxQueryService {
    private final DomainEventInboxRepository inbox;

    public InboxQueryService(DomainEventInboxRepository inbox) {
        this.inbox = inbox;
    }

    public void receive(DomainEvent event) {
        if (inbox.exists(event.id())) {
            throw new EventAlreadyExistsException();
        }
    }

    public void complete(DomainEvent event) {
        inbox.save(event.complete());
    }

    public void create(DomainEvent event) {
        inbox.save(event);
    }
}
