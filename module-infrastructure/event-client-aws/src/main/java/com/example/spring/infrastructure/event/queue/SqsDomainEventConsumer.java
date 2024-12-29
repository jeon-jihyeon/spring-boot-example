package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.QueryInboxEventHandler;
import com.example.spring.domain.player.PlayerTeamEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SqsDomainEventConsumer {
    private final QueryInboxEventHandler inboxHandler;
    private final PlayerTeamEventHandler playerTeamEventHandler;

    public SqsDomainEventConsumer(QueryInboxEventHandler inboxHandler, PlayerTeamEventHandler playerTeamEventHandler) {
        this.inboxHandler = inboxHandler;
        this.playerTeamEventHandler = playerTeamEventHandler;
    }

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.command-queue}")
    public void listenCommand(DomainEvent event) {
        playerTeamEventHandler.handle(event);
        inboxHandler.handle(event);
    }

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.team-queue}")
    public void listenTeam(DomainEvent event) {
        inboxHandler.handle(event);

    }
}
