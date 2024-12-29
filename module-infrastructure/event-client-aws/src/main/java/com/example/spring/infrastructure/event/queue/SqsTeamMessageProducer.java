package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.team.TeamMessageProducer;
import com.example.spring.infrastructure.event.AwsMessage;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqsTeamMessageProducer implements TeamMessageProducer {
    private final SqsTemplate sqsTemplate;
    private final AwsSqsProperties properties;

    public SqsTeamMessageProducer(SqsTemplate sqsTemplate, AwsSqsProperties properties) {
        this.sqsTemplate = sqsTemplate;
        this.properties = properties;
    }

    @Override
    public void send(DomainEvent event) {
        final AwsMessage message = AwsMessage.from(event);
        sqsTemplate.sendAsync(options -> options.queue(properties.teamQueue()).payload(message));
    }

    @Override
    public void sendBatch(List<DomainEvent> events) {
        if (events.isEmpty()) return;
        final List<Message<AwsMessage>> messages = events.stream()
                .map(e -> (Message<AwsMessage>) new GenericMessage<>(AwsMessage.from(e))).toList();
        sqsTemplate.sendManyAsync(properties.teamQueue(), messages);
    }
}
