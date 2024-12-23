package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.player.PlayerQueryEventHandler;
import com.example.spring.domain.team.TeamCommandEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SqsDomainEventConsumer {
    private final TeamCommandEventHandler teamEventHandler;
    private final PlayerQueryEventHandler playerEventHandler;

    public SqsDomainEventConsumer(TeamCommandEventHandler teamEventHandler, PlayerQueryEventHandler playerEventHandler) {
        this.teamEventHandler = teamEventHandler;
        this.playerEventHandler = playerEventHandler;
    }

    // TODO: sns fan-out logic
    @SqsListener(queueNames = {"team-create"})
    public void listenTeamEvent(DomainEvent event) {
        teamEventHandler.handle(event);
    }

    @SqsListener(queueNames = {"player-create"})
    public void listenPlayerEvent(DomainEvent event) {
        playerEventHandler.handle(event);
    }
}
