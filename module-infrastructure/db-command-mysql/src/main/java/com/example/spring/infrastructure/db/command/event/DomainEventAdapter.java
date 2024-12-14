package com.example.spring.infrastructure.db.command.event;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventRepository;
import org.springframework.stereotype.Component;

@Component
public class DomainEventAdapter implements DomainEventRepository {
    private final DomainEventJpaRepository repository;
    private final DomainEventMapper mapper;

    public DomainEventAdapter(DomainEventJpaRepository repository, DomainEventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(DomainEvent event) {
        repository.save(mapper.toEntity(event));
    }
}
