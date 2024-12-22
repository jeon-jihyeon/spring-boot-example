package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.infrastructure.db.OutboxNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.spring.infrastructure.db.outbox.QDomainEventEntity.domainEventEntity;

@Repository
public class DomainEventAdapter implements DomainEventOutbox {
    private final DomainEventJpaRepository repository;
    private final DomainEventJpaMapper mapper;
    private final JPAQueryFactory jpaQueryFactory;

    public DomainEventAdapter(
            DomainEventJpaRepository repository,
            DomainEventJpaMapper mapper,
            @Qualifier("outboxJPAQueryFactory") JPAQueryFactory jpaQueryFactory
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
    public DomainEvent findById(Long id) {
        return mapper.toDomain(repository.findById(id).orElseThrow(OutboxNotFoundException::new));
    }

    @Override
    public void publishAll(List<Long> ids, LocalDateTime now) {
        jpaQueryFactory.update(domainEventEntity)
                .where(domainEventEntity.id.in(ids))
                .set(domainEventEntity.published, true)
                .set(domainEventEntity.publishedAt, now)
                .execute();
    }
}
