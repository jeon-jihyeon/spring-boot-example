package com.example.spring.application.listener;

import com.example.spring.domain.event.TeamCreateEvent;
import com.example.spring.domain.player.repository.PlayerBulkCommandRepository;
import com.example.spring.domain.team.TeamId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PlayerTeamCreateListener {
    private final PlayerBulkCommandRepository repository;

    public PlayerTeamCreateListener(PlayerBulkCommandRepository repository) {
        this.repository = repository;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(TeamCreateEvent event) {
        repository.updateAll(new TeamId(event.teamId()), event.playerIds());
    }
}
