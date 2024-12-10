package com.example.spring.boot.modules.player.api;

import com.example.spring.boot.modules.player.domain.PlayersRegisterTeamService;
import com.example.spring.boot.modules.player.domain.repository.command.PlayersRegisterTeamCommand;
import com.example.spring.boot.modules.team.domain.event.TeamCreateEvent;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PlayerTeamDeleteListener {
    private final PlayersRegisterTeamService service;

    public PlayerTeamDeleteListener(PlayersRegisterTeamService service) {
        this.service = service;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(TeamCreateEvent event) {
        service.invoke(new PlayersRegisterTeamCommand(new TeamId(event.teamId()), event.playerIds()));
    }
}
