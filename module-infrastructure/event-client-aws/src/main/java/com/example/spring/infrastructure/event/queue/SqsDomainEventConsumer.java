package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.team.TeamCommandEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SqsDomainEventConsumer {
    private final TeamCommandEventHandler teamCommandEventHandler;

    public SqsDomainEventConsumer(TeamCommandEventHandler teamCommandEventHandler) {
        this.teamCommandEventHandler = teamCommandEventHandler;
    }

    // TODO: sns fan-out logic
    @Async
    @SqsListener(value = "command")
    public void listenTeam(DomainEvent event) {
        teamCommandEventHandler.handle(event);
    }
}
