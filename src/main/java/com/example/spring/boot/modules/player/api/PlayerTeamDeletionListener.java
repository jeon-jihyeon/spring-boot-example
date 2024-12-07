package com.example.spring.boot.modules.player.api;

import com.example.spring.boot.modules.player.domain.PlayerBulkUpdateTeamService;
import com.example.spring.boot.modules.player.domain.repository.command.PlayerBulkUpdateTeamCommand;
import com.example.spring.boot.modules.team.domain.event.TeamDeleteEvent;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PlayerTeamDeletionListener {
    private final PlayerBulkUpdateTeamService service;

    public PlayerTeamDeletionListener(PlayerBulkUpdateTeamService service) {
        this.service = service;
    }

    @Async
    @TransactionalEventListener
    public void handle(TeamDeleteEvent event) {
        service.invoke(new PlayerBulkUpdateTeamCommand(new TeamId(event.teamId()), TeamId.NoTeam));
    }
}
