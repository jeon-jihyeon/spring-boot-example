package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayersLeaveCommand;
import com.example.spring.domain.player.dto.PlayersRegisterCommand;
import com.example.spring.domain.team.TeamCommandApiClient;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamDeleteEvent;
import com.example.spring.domain.team.dto.TeamUpdateEvent;
import org.springframework.stereotype.Component;

@Component
public class PlayerTeamService {
    private final PlayerCommandService service;
    private final TeamCommandApiClient teamClient;

    public PlayerTeamService(PlayerCommandService service, TeamCommandApiClient teamClient) {
        this.service = service;
        this.teamClient = teamClient;
    }

    public void handle(TeamCreateEvent event) {
        final TeamData data = teamClient.findById(event.teamId());
        service.registerAll(new PlayersRegisterCommand(data.id(), data.playerIds()));
    }

    public void handle(TeamDeleteEvent event) {
        service.leaveAll(new PlayersLeaveCommand(event.teamId()));
    }

    public void handle(TeamUpdateEvent event) {
        final TeamData data = teamClient.findById(event.teamId());
        service.registerAll(new PlayersRegisterCommand(data.id(), data.playerIds()));
    }
}
