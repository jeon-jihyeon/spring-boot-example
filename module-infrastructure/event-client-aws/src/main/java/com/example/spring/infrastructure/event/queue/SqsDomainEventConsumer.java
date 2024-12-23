package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.player.PlayerEventHandler;
import com.example.spring.domain.team.TeamEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;

public class SqsDomainEventConsumer {
    private final TeamEventHandler teamEventHandler;
    private final PlayerEventHandler playerEventHandler;

    public SqsDomainEventConsumer(TeamEventHandler teamEventHandler, PlayerEventHandler playerEventHandler) {
        this.teamEventHandler = teamEventHandler;
        this.playerEventHandler = playerEventHandler;
    }

    @SqsListener("team-create")
    public void listenTeamEvent(DomainEvent event) {
        teamEventHandler.handle(event);
    }

    @SqsListener("player-create")
    public void listenPlayerEvent(DomainEvent event) {
        playerEventHandler.handle(event);
    }
}
