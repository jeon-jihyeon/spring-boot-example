package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SqsDomainEventConsumer {


    // TODO: sns fan-out logic
    @Async
    @SqsListener(value = "cqrs-create")
    public void listen(DomainEvent event) {
    }
}
