package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.event.DomainEventOutboxService;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;

@Component
public class CommandPreInsertEventListener implements PreInsertEventListener {
    private final DomainEventOutboxService outboxService;

    public CommandPreInsertEventListener(DomainEventOutboxService outboxService) {
        this.outboxService = outboxService;
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        final Object e = event.getEntity();
        if (e instanceof BaseCommandEntity) outboxService.create(((BaseCommandEntity) e).getCommand());
        return false;
    }
}
