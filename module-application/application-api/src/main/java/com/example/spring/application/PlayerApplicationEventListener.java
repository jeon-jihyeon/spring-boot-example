package com.example.spring.application;

import com.example.spring.domain.command.player.PlayerTeamMessageService;
import com.example.spring.domain.command.player.dto.PlayerTeamEvent;
import com.example.spring.domain.command.player.model.PlayerId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PlayerApplicationEventListener {
    private final PlayerTeamMessageService service;

    public PlayerApplicationEventListener(PlayerTeamMessageService service) {
        this.service = service;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listen(PlayerTeamEvent event) {
        service.send(new PlayerId(event.playerId()));
    }
}
