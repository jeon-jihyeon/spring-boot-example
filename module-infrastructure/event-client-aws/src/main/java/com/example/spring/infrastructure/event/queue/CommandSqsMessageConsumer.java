package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.player.PlayerQueryHandler;
import com.example.spring.domain.team.TeamPlayerHandler;
import com.example.spring.domain.team.TeamQueryHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandSqsMessageConsumer {
    private final PlayerQueryHandler playerHandler;
    private final TeamQueryHandler teamHandler;
    private final TeamPlayerHandler teamPlayerHandler;

    public CommandSqsMessageConsumer(PlayerQueryHandler playerHandler, TeamQueryHandler teamHandler, TeamPlayerHandler teamPlayerHandler) {
        this.playerHandler = playerHandler;
        this.teamHandler = teamHandler;
        this.teamPlayerHandler = teamPlayerHandler;
    }

    @Async
    @SqsListener(value = "team")
    public void listenTeam(DomainEvent event) {
        teamHandler.handle(event);
    }

    @Async
    @SqsListener(value = "player")
    public void listenPlayer(DomainEvent event) {
        playerHandler.handle(event);
    }

    @Async
    @SqsListener(value = "player-team")
    public void listenPlayerTeam(DomainEvent event) {
        teamPlayerHandler.handle(event);
    }
}
