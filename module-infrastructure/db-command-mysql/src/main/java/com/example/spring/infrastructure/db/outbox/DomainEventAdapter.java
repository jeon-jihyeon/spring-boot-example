package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DomainEventAdapter implements DomainEventOutbox {
    private final DomainEventJpaRepository repository;
    private final DomainEventMapper mapper;

    public DomainEventAdapter(DomainEventJpaRepository repository, DomainEventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(DomainEvent event) {
        repository.save(mapper.toEntity(event));
    }

    @Override
    public DomainEvent findById(Long id) {
        return mapper.toDomain(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }
}
