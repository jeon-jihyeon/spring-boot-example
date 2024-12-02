package com.example.spring.boot.modules.player.api;

import com.example.spring.boot.modules.team.domain.event.TeamDeleteEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PlayerDeleteByTeamHandler {

    @Async
    @EventListener
    public void handle(TeamDeleteEvent event) {
        // TODO:
    }
}
