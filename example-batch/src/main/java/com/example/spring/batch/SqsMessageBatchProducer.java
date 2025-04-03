package com.example.spring.batch;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqsMessageBatchProducer implements MessageBatchProducer {
    private final SqsTemplate sqsTemplate;

    public SqsMessageBatchProducer(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public void sendBatch(List<DomainEvent> events) {
        if (events.isEmpty()) return;
        final List<Message<AwsMessage>> messages = events.stream()
                .map(e -> (Message<AwsMessage>) new GenericMessage<>(AwsMessage.from(e))).toList();
        sqsTemplate.sendManyAsync(events.get(0).queueName(), messages);
    }
}
