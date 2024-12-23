package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.TeamEventProducer;
import com.example.spring.infrastructure.event.AwsMessage;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqsTeamEventProducer implements TeamEventProducer {
    private final SqsTemplate sqsTemplate;
    private final AwsSqsQueueNames queueNames;

    public SqsTeamEventProducer(SqsTemplate sqsTemplate, AwsSqsQueueNames queueNames) {
        this.sqsTemplate = sqsTemplate;
        this.queueNames = queueNames;
    }

    @Override
    public void push(DomainEvent event) {
        sqsTemplate.sendAsync(options -> options.queue(queueNames.teamCreate()).payload(AwsMessage.from(event)));
    }

    @Override
    public void pushAll(List<DomainEvent> events) {
        final List<Message<AwsMessage>> messages = events.stream()
                .map(e -> (Message<AwsMessage>) new GenericMessage<>(AwsMessage.from(e))).toList();
        sqsTemplate.sendManyAsync(queueNames.teamCreate(), messages);
    }
}
