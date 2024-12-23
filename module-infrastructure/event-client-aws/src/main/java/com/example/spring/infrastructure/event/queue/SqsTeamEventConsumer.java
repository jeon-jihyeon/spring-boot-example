package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.TeamEventConsumer;
import com.example.spring.domain.event.TeamEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SqsTeamEventConsumer implements TeamEventConsumer {
    private final TeamEventHandler eventHandler;

    public SqsTeamEventConsumer(TeamEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    @SqsListener("${spring.cloud.aws.sqs.queue.name.team-create}")
    public void pull(DomainEvent event) {
        eventHandler.handle(event);
    }
}
