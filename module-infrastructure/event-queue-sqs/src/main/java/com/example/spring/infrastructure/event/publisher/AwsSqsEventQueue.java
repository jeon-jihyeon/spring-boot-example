package com.example.spring.infrastructure.event.publisher;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventQueue;
import com.example.spring.domain.player.PlayerTeamCreateEventHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AwsSqsEventQueue implements DomainEventQueue {
    private final SqsTemplate sqsTemplate;
    private final AwsSqsProperties properties;
    private final PlayerTeamCreateEventHandler eventHandler;

    public AwsSqsEventQueue(SqsTemplate sqsTemplate, AwsSqsProperties properties, PlayerTeamCreateEventHandler eventHandler) {
        this.sqsTemplate = sqsTemplate;
        this.properties = properties;
        this.eventHandler = eventHandler;
    }

    @Override
    public void push(DomainEvent event) {
        sqsTemplate.sendAsync(options -> options.queue(properties.queueName()).payload(AwsSqsEvent.from(event)));
    }

    @Override
    @SqsListener("${spring.cloud.aws.sqs.queue.name}")
    public void pull(DomainEvent event) {
        eventHandler.invoke(event);
    }

    @Override
    public void pushAll(List<DomainEvent> events) {
        final List<Message<AwsSqsEvent>> messages = events.stream()
                .map(e -> (Message<AwsSqsEvent>) new GenericMessage<>(AwsSqsEvent.from(e))).toList();
        sqsTemplate.sendManyAsync(properties.queueName(), messages);
    }
}
