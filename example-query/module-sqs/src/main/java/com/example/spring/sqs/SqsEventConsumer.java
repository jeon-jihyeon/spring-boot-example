package com.example.spring.sqs;

import com.example.spring.domain.event.InboxEvent;
import com.example.spring.domain.query.PlayerQueryHandler;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SqsEventConsumer {
    private final PlayerQueryHandler playerHandler;

    public SqsEventConsumer(PlayerQueryHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    @Async
    @SqsListener(value = "${spring.cloud.aws.sqs.queue.player}")
    public void listenPlayer(InboxEvent event) {
        playerHandler.handle(event);
    }
}
