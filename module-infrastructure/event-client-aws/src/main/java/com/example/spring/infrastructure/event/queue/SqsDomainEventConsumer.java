package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.player.PlayerQueryEventHandler;
import com.example.spring.domain.team.TeamCommandEventHandler;
import com.example.spring.domain.team.TeamQueryEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SqsDomainEventConsumer {
    private final TeamCommandEventHandler teamCommandEventHandler;
    private final TeamQueryEventHandler teamQueryEventHandler;
    private final PlayerQueryEventHandler playerQueryEventHandler;

    public SqsDomainEventConsumer(
            TeamCommandEventHandler teamCommandEventHandler,
            TeamQueryEventHandler teamQueryEventHandler,
            PlayerQueryEventHandler playerQueryEventHandler
    ) {
        this.teamCommandEventHandler = teamCommandEventHandler;
        this.teamQueryEventHandler = teamQueryEventHandler;
        this.playerQueryEventHandler = playerQueryEventHandler;
    }

    // TODO: sns fan-out logic
    @Async
    @SqsListener(value = "team-create-domain")
    public void listenTeam(DomainEvent event) {
        teamCommandEventHandler.handle(event);
    }

    @Async
    @SqsListener(value = "team-create-persistence")
    public void listenTeamEntity(DomainEvent event) {
        teamQueryEventHandler.handle(event);
    }

    @Async
    @SqsListener(value = "player-create-persistence")
    public void listenPlayerEntity(DomainEvent event) {
        playerQueryEventHandler.handle(event);
    }
}
