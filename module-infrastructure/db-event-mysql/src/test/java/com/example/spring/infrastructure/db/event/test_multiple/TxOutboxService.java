package com.example.spring.infrastructure.db.event.test_multiple;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.infrastructure.db.event.outbox.OutboxEventEntity;
import com.example.spring.infrastructure.db.event.outbox.OutboxEventJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TxOutboxService {
    private final OutboxEventJpaRepository outbox;

    public TxOutboxService(OutboxEventJpaRepository outbox) {
        this.outbox = outbox;
    }

    void save(DomainEvent event) {
        outbox.save(OutboxEventEntity.from(event));
    }

    @Transactional(transactionManager = "outboxTransactionManager")
    void saveInTx(DomainEvent event) {
        outbox.save(OutboxEventEntity.from(event));
    }

    @Transactional(transactionManager = "outboxTransactionManager", propagation = Propagation.REQUIRES_NEW)
    void saveInNewTx(DomainEvent event) {
        outbox.save(OutboxEventEntity.from(event));
    }
}