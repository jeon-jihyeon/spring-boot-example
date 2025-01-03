package com.example.spring.infrastructure.db.event.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInbox;
import com.example.spring.infrastructure.db.event.EventNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class InboxEventAdapter implements DomainEventInbox {
    private final InboxEventJpaRepository repository;

    public InboxEventAdapter(InboxEventJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(transactionManager = "inboxTransactionManager")
    public void save(DomainEvent event) {
        repository.save(InboxEventEntity.from(event));
    }

    @Override
    public DomainEvent findEvent(Long id) {
        return repository.findById(id).orElseThrow(EventNotFoundException::new).toModel();
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
