package com.example.spring.infrastructure.event.queue;

import com.example.spring.domain.event.DomainEvent;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SqsDomainEventConsumer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.command-queue}")
    public void listenCommand(DomainEvent event) {
        log.info("[Command Queue Consumer] {}", event);
    }

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.team-queue}")
    public void listenTeam(DomainEvent event) {
        log.info("[Team Queue Consumer] {}", event);
    }
}
