package com.example.spring.infrastructure.db.event.outbox;

import com.example.spring.domain.event.QueryInboxService;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
public class OutboxPostUpdateEventListener implements PostUpdateEventListener {
    private final QueryInboxService inboxService;

    public OutboxPostUpdateEventListener(QueryInboxService inboxService) {
        this.inboxService = inboxService;
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        final Object e = event.getEntity();
        if (e instanceof OutboxEventEntity) inboxService.create(((OutboxEventEntity) e).toModel());
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
