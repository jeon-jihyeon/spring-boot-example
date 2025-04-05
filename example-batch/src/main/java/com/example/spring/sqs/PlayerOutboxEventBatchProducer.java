package com.example.spring.sqs;

import com.example.spring.outbox.OutboxEvent;
import com.example.spring.outbox.OutboxEventBatchProducer;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerOutboxEventBatchProducer implements OutboxEventBatchProducer {
    private final SqsTemplate sqsTemplate;
    private final SqsQueueNames queueNames;

    public PlayerOutboxEventBatchProducer(SqsTemplate sqsTemplate, SqsQueueNames queueNames) {
        this.sqsTemplate = sqsTemplate;
        this.queueNames = queueNames;
    }

    @Override
    public void sendBatch(List<OutboxEvent> events) {
        if (events.isEmpty()) return;
        final List<Message<SqsMessage>> messages = events.stream()
                .map(e -> (Message<SqsMessage>) new GenericMessage<>(SqsMessage.from(e))).toList();
        sqsTemplate.sendManyAsync(queueNames.player(), messages);
    }
}
