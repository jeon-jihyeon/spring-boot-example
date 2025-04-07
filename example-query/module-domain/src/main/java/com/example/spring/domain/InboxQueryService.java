package com.example.spring.domain;

import com.example.spring.domain.event.EventAlreadyExistsException;
import com.example.spring.domain.event.InboxEvent;
import com.example.spring.domain.event.InboxEventRepository;
import org.springframework.stereotype.Component;

@Component
public class InboxQueryService {
    private final InboxEventRepository inbox;

    public InboxQueryService(InboxEventRepository inbox) {
        this.inbox = inbox;
    }

    public void receive(InboxEvent event) {
        if (inbox.exists(event)) {
            throw new EventAlreadyExistsException();
        }
    }

    public void complete(InboxEvent event) {
        inbox.save(event.complete());
    }

    public void create(InboxEvent event) {
        inbox.save(event);
    }
}
