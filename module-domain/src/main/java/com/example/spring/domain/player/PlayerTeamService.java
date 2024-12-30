package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayersLeaveCommand;
import com.example.spring.domain.player.dto.PlayersRegisterCommand;
import com.example.spring.domain.team.TeamCommandApiClient;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.stereotype.Component;

@Component
public class PlayerTeamService {
    private final PlayerCommandService service;
    private final TeamCommandApiClient teamClient;

    public PlayerTeamService(PlayerCommandService service, TeamCommandApiClient teamClient) {
        this.service = service;
        this.teamClient = teamClient;
    }

    public void handleCreate(TeamId teamId) {
        final TeamData data = teamClient.findById(teamId);
        service.registerAll(new PlayersRegisterCommand(data.id(), data.playerIds()));
    }

    public void handleDelete(TeamId teamId) {
        service.leaveAll(new PlayersLeaveCommand(teamId));
    }

    public void handleUpdate(TeamId teamId) {
        final TeamData data = teamClient.findById(teamId);
        service.registerAll(new PlayersRegisterCommand(data.id(), data.playerIds()));
    }
}
