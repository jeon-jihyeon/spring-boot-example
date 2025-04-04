package com.example.spring.domain.outbox;

import org.springframework.stereotype.Service;

@Service
public class OutboxEventCompleteService {
    private final OutboxEventRepository outboxRepository;

    public OutboxEventCompleteService(OutboxEventRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    public void invoke(OutboxEvent event) {
        outboxRepository.save(event.complete());
    }
}
