package com.example.spring.infrastructure.db.event.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.spring.infrastructure.db.event.outbox.QOutboxEventEntity.outboxEventEntity;


@Repository
public class OutboxEventAdapter implements DomainEventOutbox {
    private final OutboxEventJpaRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

    public OutboxEventAdapter(
            OutboxEventJpaRepository repository,
            @Qualifier("outboxJPAQueryFactory") JPAQueryFactory jpaQueryFactory
    ) {
        this.repository = repository;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    @Transactional
    public void save(DomainEvent event) {
        repository.save(OutboxEventEntity.from(event));
    }

    @Override
    public void publishAll(List<Long> ids, LocalDateTime now) {
        jpaQueryFactory.update(outboxEventEntity)
                .where(outboxEventEntity.id.in(ids))
                .set(outboxEventEntity.completed, true)
                .set(outboxEventEntity.completedAt, now)
                .execute();
    }
}
