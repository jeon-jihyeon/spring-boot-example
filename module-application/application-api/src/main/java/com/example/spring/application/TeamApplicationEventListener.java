package com.example.spring.application;

import com.example.spring.domain.command.team.TeamDeleteMessageService;
import com.example.spring.domain.command.team.dto.TeamDeleteEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TeamApplicationEventListener {
    private final TeamDeleteMessageService service;

    public TeamApplicationEventListener(TeamDeleteMessageService service) {
        this.service = service;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listen(TeamDeleteEvent event) {
        service.send(event);
    }
}
