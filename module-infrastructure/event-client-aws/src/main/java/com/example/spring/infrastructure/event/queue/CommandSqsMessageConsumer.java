package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.command.player.PlayerCommandHandler;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.query.player.PlayerQueryHandler;
import com.example.spring.domain.query.team.TeamQueryHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandSqsMessageConsumer {
    private final PlayerQueryHandler playerHandler;
    private final TeamQueryHandler teamHandler;
    private final PlayerCommandHandler playerCommandHandler;

    public CommandSqsMessageConsumer(
            PlayerQueryHandler playerHandler,
            TeamQueryHandler teamHandler,
            PlayerCommandHandler playerCommandHandler
    ) {
        this.playerHandler = playerHandler;
        this.teamHandler = teamHandler;
        this.playerCommandHandler = playerCommandHandler;
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

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.queue.delete-team}")
    public void listenPlayerTeam(DomainEvent event) {
        playerCommandHandler.handle(event);
    }
}
