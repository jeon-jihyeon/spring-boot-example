package com.example.spring.domain.outbox;

import com.example.spring.domain.command.model.PlayerId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboxEventSaveService {
    private final OutboxEventRepository outboxRepository;

    public OutboxEventSaveService(OutboxEventRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public void savePlayerCreatedEvent(PlayerId id) {
        outboxRepository.save(OutboxEvent.of(OutboxEventType.CREATE_PLAYER, id.value()));
    }

    @Transactional
    public void savePlayerDeletedEvent(PlayerId id) {
        outboxRepository.save(OutboxEvent.of(OutboxEventType.DELETE_PLAYER, id.value()));
    }

    @Transactional
    public void savePlayerAddedPointEvent(PlayerId id) {
        outboxRepository.save(OutboxEvent.of(OutboxEventType.ADD_POINT, id.value()));
    }
}
