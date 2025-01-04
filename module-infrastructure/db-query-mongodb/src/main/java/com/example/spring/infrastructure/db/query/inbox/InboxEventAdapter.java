package com.example.spring.infrastructure.db.query.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInboxRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InboxEventAdapter implements DomainEventInboxRepository {
    private final InboxEventMongoRepository repository;

    public InboxEventAdapter(InboxEventMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(DomainEvent event) {
        repository.save(InboxEventDocument.from(event));
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
