package com.example.spring.application;

import com.example.spring.domain.command.player.PlayerCommandMessageService;
import com.example.spring.domain.command.player.dto.PlayerLeaveAllEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PlayerApplicationEventListener {
    private final PlayerCommandMessageService service;

    public PlayerApplicationEventListener(PlayerCommandMessageService service) {
        this.service = service;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listen(PlayerLeaveAllEvent event) {
        service.sendBatch(event);
    }
}
