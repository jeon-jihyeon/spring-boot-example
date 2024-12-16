package com.example.spring.application;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.DomainEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ApplicationEventListener {
    private final DomainEventPublisher publisher;
    private final DomainEventOutbox outbox;

    public ApplicationEventListener(DomainEventPublisher publisher, DomainEventOutbox outbox) {
        this.publisher = publisher;
        this.outbox = outbox;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(DomainEvent event) {
        outbox.save(event);
        publisher.publish(event);
    }
}
