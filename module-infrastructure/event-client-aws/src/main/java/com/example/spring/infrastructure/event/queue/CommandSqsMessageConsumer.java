package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.player.PlayerTeamEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandSqsMessageConsumer {
    private final PlayerTeamEventHandler playerTeamEventHandler;

    public CommandSqsMessageConsumer(PlayerTeamEventHandler playerTeamEventHandler) {
        this.playerTeamEventHandler = playerTeamEventHandler;
    }

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.queue.team}")
    public void listenTeam(DomainEvent event) {
        playerTeamEventHandler.handle(event);

    }

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.queue.player}")
    public void listenPlayer(DomainEvent event) {

    }
}
