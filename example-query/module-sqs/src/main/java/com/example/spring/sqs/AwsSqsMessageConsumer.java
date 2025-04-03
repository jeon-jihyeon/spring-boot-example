package com.example.spring.sqs;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.query.PlayerQueryHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AwsSqsMessageConsumer {
    private final PlayerQueryHandler playerHandler;

    public AwsSqsMessageConsumer(PlayerQueryHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.queue.player}")
    public void listenPlayer(DomainEvent event) {
        playerHandler.handle(event);
    }
}
