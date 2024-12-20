package com.example.spring.infrastructure.event.publisher;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventQueue;
import com.example.spring.domain.player.PlayerTeamCreateListener;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class AwsSqsEventQueue implements DomainEventQueue {
    private final SqsTemplate sqsTemplate;
    private final AwsSqsProperties properties;
    private final PlayerTeamCreateListener listener;

    public AwsSqsEventQueue(SqsTemplate sqsTemplate, AwsSqsProperties properties, PlayerTeamCreateListener listener) {
        this.sqsTemplate = sqsTemplate;
        this.properties = properties;
        this.listener = listener;
    }

    @Override
    public void push(DomainEvent event) {
        sqsTemplate.send(options -> options.queue(properties.queueName()).payload(AwsSqsEvent.from(event)));
    }

    @Override
    @SqsListener("${spring.cloud.aws.sqs.queue.name}")
    public void pull(DomainEvent event) {
        listener.invoke(event);
    }
}
