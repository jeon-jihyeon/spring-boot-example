package com.example.spring.application;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventLayer;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TeamCreateEventListener {
    private final DomainEventOutbox outbox;

    public TeamCreateEventListener(DomainEventOutbox outbox) {
        this.outbox = outbox;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(TeamCreateEvent team) {
        outbox.save(DomainEvent.createType(DomainEventLayer.DOMAIN, Team.class.getSimpleName(), team.id().value()));
    }
}
