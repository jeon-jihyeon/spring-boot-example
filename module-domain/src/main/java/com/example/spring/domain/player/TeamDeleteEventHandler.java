package com.example.spring.domain.player;

import com.example.spring.domain.team.dto.TeamDeleteEvent;
import org.springframework.stereotype.Component;

@Component
public class TeamDeleteEventHandler {
    private final PlayerCommandRepository playerRepository;

    public TeamDeleteEventHandler(PlayerCommandRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void handle(TeamDeleteEvent event) {
        playerRepository.updateAll(event.teamId());
    }
}
