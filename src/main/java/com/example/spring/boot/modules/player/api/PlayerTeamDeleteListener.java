package com.example.spring.boot.modules.player.api;

import com.example.spring.boot.modules.player.domain.PlayersUpdateTeamService;
import com.example.spring.boot.modules.player.domain.repository.command.PlayersUpdateTeamCommand;
import com.example.spring.boot.modules.team.domain.event.TeamDeleteEvent;
import com.example.spring.boot.modules.team.domain.model.TeamId;
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
