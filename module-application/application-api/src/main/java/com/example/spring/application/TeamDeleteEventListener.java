package com.example.spring.application;

import com.example.spring.domain.player.TeamDeleteEventHandler;
import com.example.spring.domain.team.dto.TeamDeleteEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TeamDeleteEventListener {
    private final TeamDeleteEventHandler handler;

    public TeamDeleteEventListener(TeamDeleteEventHandler handler) {
        this.handler = handler;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(TeamDeleteEvent event) {
        handler.handle(event);
    }
}
