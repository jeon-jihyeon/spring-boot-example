package com.example.spring.sqs.queue;

import com.example.spring.domain.outbox.OutboxEvent;
import com.example.spring.domain.outbox.OutboxMessageProducer;
import com.example.spring.sqs.AwsMessage;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqsOutboxMessageProducer implements OutboxMessageProducer {
    private final SqsTemplate sqsTemplate;

    public SqsOutboxMessageProducer(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public void send(OutboxEvent event) {
        final AwsMessage message = AwsMessage.from(event);
        sqsTemplate.sendAsync(options -> options.queue(message.queueName()).payload(message));
    }
}
