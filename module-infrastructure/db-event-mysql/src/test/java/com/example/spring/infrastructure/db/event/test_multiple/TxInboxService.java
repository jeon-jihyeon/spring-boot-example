package com.example.spring.infrastructure.db.event.test_multiple;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.infrastructure.db.event.inbox.InboxEventEntity;
import com.example.spring.infrastructure.db.event.inbox.InboxEventJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TxInboxService {
    private final InboxEventJpaRepository inbox;

    public TxInboxService(InboxEventJpaRepository inbox) {
        this.inbox = inbox;
    }

    void save(DomainEvent event) {
        inbox.save(InboxEventEntity.from(event));
    }

    @Transactional(transactionManager = "inboxTransactionManager")
    void saveInTx(DomainEvent event) {
        inbox.save(InboxEventEntity.from(event));
    }

    @Transactional(transactionManager = "inboxTransactionManager")
    void saveWithException(DomainEvent event) {
        inbox.save(InboxEventEntity.from(event));
        throw new MultipleDbException();
    }
}