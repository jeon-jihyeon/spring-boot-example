package com.example.spring.mysql.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutboxRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class OutboxEventAdapter implements DomainEventOutboxRepository {
    private final OutboxEventJpaRepository repository;

    public OutboxEventAdapter(OutboxEventJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void save(DomainEvent event) {
        repository.save(OutboxEventEntity.from(event));
    }
}
