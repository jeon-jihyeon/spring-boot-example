package com.example.spring.infrastructure.event.publisher;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventPublisher;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class AwsSQSDomainEventPublisher implements DomainEventPublisher {
    private final SqsTemplate sqsTemplate;

    public AwsSQSDomainEventPublisher(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        sqsTemplate.send(to -> to.payload(event));
    }
}
