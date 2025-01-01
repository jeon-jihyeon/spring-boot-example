package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.MessageProducer;
import com.example.spring.infrastructure.event.AwsMessage;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqsMessageProducer implements MessageProducer {
    private final SqsTemplate sqsTemplate;

    public SqsMessageProducer(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public void send(DomainEvent event) {
        final AwsMessage message = AwsMessage.from(event);
        sqsTemplate.sendAsync(options -> options.queue(event.queueName()).payload(message));
    }
}
