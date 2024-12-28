package com.example.spring.application;

import com.example.spring.domain.player.TeamCreateEventHandler;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TeamCreateEventListener {
    private final TeamCreateEventHandler handler;

    public TeamCreateEventListener(TeamCreateEventHandler handler) {
        this.handler = handler;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(TeamCreateEvent event) {
        handler.handle(event);
    }
}
