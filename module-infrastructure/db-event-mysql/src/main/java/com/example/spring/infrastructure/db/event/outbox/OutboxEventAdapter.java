package com.example.spring.infrastructure.db.event.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class OutboxEventAdapter implements DomainEventOutbox {
    private final OutboxEventJpaRepository repository;

    public OutboxEventAdapter(OutboxEventJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(transactionManager = "outboxTransactionManager")
    public void save(DomainEvent event) {
        repository.save(OutboxEventEntity.from(event));
    }
}
