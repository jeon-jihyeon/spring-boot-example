package com.example.spring.domain.player;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class TeamCreateEventHandler {
    private final PlayerCommandRepository playerRepository;

    public TeamCreateEventHandler(PlayerCommandRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void handle(TeamCreateEvent event) {
        playerRepository.updateAll(event.teamId(), event.playerIds().stream().map(PlayerId::value).toList());
    }
}
