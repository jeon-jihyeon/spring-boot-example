package com.example.spring.infrastructure.event.publisher;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.TeamCreateEventQueue;
import com.example.spring.domain.player.TeamCreateEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqsTeamCreateEventQueue implements TeamCreateEventQueue {
    private final SqsTemplate sqsTemplate;
    private final AwsSqsQueueNames queueNames;
    private final TeamCreateEventHandler eventHandler;

    public SqsTeamCreateEventQueue(SqsTemplate sqsTemplate, AwsSqsQueueNames queueNames, TeamCreateEventHandler eventHandler) {
        this.sqsTemplate = sqsTemplate;
        this.queueNames = queueNames;
        this.eventHandler = eventHandler;
    }

    @Override
    public void push(DomainEvent event) {
        sqsTemplate.sendAsync(options -> options.queue(queueNames.teamCreate()).payload(AwsMessage.from(event)));
    }

    @Override
    @SqsListener("${spring.cloud.aws.sqs.queue.name}")
    public void pull(DomainEvent event) {
        eventHandler.invoke(event);
    }

    @Override
    public void pushAll(List<DomainEvent> events) {
        final List<Message<AwsMessage>> messages = events.stream()
                .map(e -> (Message<AwsMessage>) new GenericMessage<>(AwsMessage.from(e))).toList();
        sqsTemplate.sendManyAsync(queueNames.teamCreate(), messages);
    }
}
