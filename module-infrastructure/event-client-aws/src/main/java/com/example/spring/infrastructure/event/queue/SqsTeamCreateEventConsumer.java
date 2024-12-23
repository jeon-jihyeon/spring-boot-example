package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.TeamCreateEventConsumer;
import com.example.spring.domain.event.TeamCreateEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SqsTeamCreateEventConsumer implements TeamCreateEventConsumer {
    private final TeamCreateEventHandler eventHandler;

    public SqsTeamCreateEventConsumer(TeamCreateEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    @SqsListener("${spring.cloud.aws.sqs.queue.name.team-create}")
    public void pull(DomainEvent event) {
        eventHandler.invoke(event);
    }
}
