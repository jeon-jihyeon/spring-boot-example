package com.example.spring.domain.event;

import com.example.spring.domain.event.model.DomainEvent;
import org.springframework.stereotype.Service;

@Service
public class OutboxCompleteService {
    private final DomainEventOutboxRepository outboxRepository;

    public OutboxCompleteService(DomainEventOutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    public void invoke(DomainEvent event) {
        outboxRepository.save(event.complete());
    }
}
