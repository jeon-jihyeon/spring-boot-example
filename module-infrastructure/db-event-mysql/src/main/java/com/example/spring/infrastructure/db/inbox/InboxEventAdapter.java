package com.example.spring.infrastructure.db.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInbox;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.spring.infrastructure.db.inbox.QInboxEventEntity.inboxEventEntity;

@Repository
public class InboxEventAdapter implements DomainEventInbox {
    private final InboxEventJpaRepository repository;
    private final InboxEventJpaMapper mapper;
    private final JPAQueryFactory jpaQueryFactory;

    public InboxEventAdapter(
            InboxEventJpaRepository repository,
            InboxEventJpaMapper mapper,
            @Qualifier("inboxJPAQueryFactory") JPAQueryFactory jpaQueryFactory
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    @Transactional
    public void save(DomainEvent event) {
        repository.save(mapper.toEntity(event));
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void processAll(List<Long> ids, LocalDateTime now) {
        jpaQueryFactory.update(inboxEventEntity)
                .where(inboxEventEntity.id.in(ids))
                .set(inboxEventEntity.completed, true)
                .set(inboxEventEntity.completedAt, now)
                .execute();
    }
}
