package com.example.spring.application.listener;

import com.example.spring.domain.event.*;
import com.example.spring.domain.team.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PlayerTeamCreateListener {
    private final DomainEventPublisher publisher;
    private final DomainEventRepository repository;
    private final ObjectMapper objectMapper;

    public PlayerTeamCreateListener(DomainEventPublisher publisher, DomainEventRepository repository, ObjectMapper objectMapper) {
        this.publisher = publisher;
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(TeamCreateEvent event) throws JsonProcessingException {
        final String data = objectMapper.writeValueAsString(event.playerIds());
        final DomainEvent domainEvent = DomainEvent.of(Team.class.getName(), event.teamId(), DomainEventType.CREATE, data);
        repository.save(domainEvent);
        publisher.publish(domainEvent);
    }
}
