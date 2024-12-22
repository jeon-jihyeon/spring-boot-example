package com.example.spring.application;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.TeamCreateEventQueue;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ApplicationEventListener {
    private final TeamCreateEventQueue queue;
    private final DomainEventOutbox outbox;

    public ApplicationEventListener(TeamCreateEventQueue queue, DomainEventOutbox outbox) {
        this.queue = queue;
        this.outbox = outbox;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(DomainEvent event) {
        final DomainEvent published = event.publish();
        queue.push(published);
        outbox.save(published);
    }
}
