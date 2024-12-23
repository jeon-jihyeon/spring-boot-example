package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.TeamCreateEventConsumer;
import com.example.spring.domain.player.TeamCreateEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqsTeamCreateEventConsumer implements TeamCreateEventConsumer {
    private final SqsTemplate sqsTemplate;
    private final AwsSqsQueueNames queueNames;
    private final TeamCreateEventHandler eventHandler;

    public SqsTeamCreateEventConsumer(SqsTemplate sqsTemplate, AwsSqsQueueNames queueNames, TeamCreateEventHandler eventHandler) {
        this.sqsTemplate = sqsTemplate;
        this.queueNames = queueNames;
        this.eventHandler = eventHandler;
    }

    @Override
    @SqsListener("${spring.cloud.aws.sqs.queue.name.team-create}")
    public void pull(DomainEvent event) {
        eventHandler.invoke(event);
    }
}
