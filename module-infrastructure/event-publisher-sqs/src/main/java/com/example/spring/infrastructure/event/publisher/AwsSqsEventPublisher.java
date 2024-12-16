package com.example.spring.infrastructure.event.publisher;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.DomainEventPublisher;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class AwsSqsEventPublisher implements DomainEventPublisher {
    private final SqsTemplate sqsTemplate;
    private final AwsSqsProperties properties;
    private final DomainEventOutbox outbox;

    public AwsSqsEventPublisher(SqsTemplate sqsTemplate, AwsSqsProperties properties, DomainEventOutbox outbox) {
        this.sqsTemplate = sqsTemplate;
        this.properties = properties;
        this.outbox = outbox;
    }

    @Override
    public void publish(DomainEvent event) {
        sqsTemplate.send(options -> options.queue(properties.queueName()).payload(AwsSqsEvent.from(event)));
        outbox.save(event.publish());
    }
}
