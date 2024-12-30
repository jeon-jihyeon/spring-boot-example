package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.player.PlayerMessageHandler;
import com.example.spring.domain.team.TeamMessageHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandSqsMessageConsumer {
    private final PlayerMessageHandler playerHandler;
    private final TeamMessageHandler teamHandler;

    public CommandSqsMessageConsumer(PlayerMessageHandler playerHandler, TeamMessageHandler teamHandler) {
        this.playerHandler = playerHandler;
        this.teamHandler = teamHandler;
    }

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.queue.team}")
    public void listenTeam(DomainEvent event) {
        teamHandler.handle(event);
    }

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.queue.player}")
    public void listenPlayer(DomainEvent event) {
        playerHandler.handle(event);
    }
}
