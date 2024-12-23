package com.example.spring.application;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.DomainEventProducer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TeamCreateEventListener {
    private final DomainEventProducer producer;
    private final DomainEventOutbox outbox;

    public TeamCreateEventListener(DomainEventProducer producer, DomainEventOutbox outbox) {
        this.producer = producer;
        this.outbox = outbox;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(DomainEvent event) throws Exception {
        final DomainEvent completed = event.complete();
        producer.send(completed);
        outbox.save(completed);
    }
}
