package com.example.spring.application;

import com.example.spring.domain.team.TeamOutboxService;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import com.example.spring.domain.team.dto.TeamDeleteEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TeamEventListener {
    private final TeamOutboxService service;

    public TeamEventListener(TeamOutboxService service) {
        this.service = service;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCreate(TeamCreateEvent event) {
        service.sendCreateType(event);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDelete(TeamDeleteEvent event) {
        service.sendDeleteType(event);
    }
}
