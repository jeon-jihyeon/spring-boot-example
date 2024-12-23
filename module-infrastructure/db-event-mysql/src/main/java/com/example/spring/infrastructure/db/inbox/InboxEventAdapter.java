package com.example.spring.infrastructure.db.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInbox;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class InboxEventAdapter implements DomainEventInbox {
    private final InboxEventJpaRepository repository;
    private final InboxEventJpaMapper mapper;

    public InboxEventAdapter(
            InboxEventJpaRepository repository,
            InboxEventJpaMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(DomainEvent event) {
        repository.save(mapper.toEntity(event));
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
