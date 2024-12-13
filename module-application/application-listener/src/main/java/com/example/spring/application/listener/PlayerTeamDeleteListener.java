package com.example.spring.application.listener;

import com.example.spring.domain.player.repository.command.PlayersUpdateTeamCommand;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.event.TeamDeleteEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PlayerTeamDeleteListener {
    private final PlayersUpdateTeamService service;

    public PlayerTeamDeleteListener(PlayersUpdateTeamService service) {
        this.service = service;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(TeamDeleteEvent event) {
        service.invoke(new PlayersUpdateTeamCommand(new TeamId(event.teamId()), TeamId.NoTeam));
    }
}
