package com.example.spring.sqs;

import com.example.spring.domain.outbox.OutboxEvent;
import com.example.spring.domain.outbox.OutboxEventProducer;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlayerOutboxEventProducer implements OutboxEventProducer {
    private final SqsTemplate sqsTemplate;
    private final SqsQueueNames queueNames;

    public PlayerOutboxEventProducer(SqsTemplate sqsTemplate, SqsQueueNames queueNames) {
        this.sqsTemplate = sqsTemplate;
        this.queueNames = queueNames;
    }

    @Override
    public void send(OutboxEvent event) {
        final SqsMessage message = SqsMessage.from(event);
        sqsTemplate.sendAsync(options -> options.queue(queueNames.player()).payload(message));
    }
}
