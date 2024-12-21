package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.spring.infrastructure.db.outbox.QDomainEventEntity.domainEventEntity;

@Repository
public class DomainEventAdapter implements DomainEventOutbox {
    private final DomainEventJpaRepository repository;
    private final DomainEventJpaMapper mapper;
    private final JPAQueryFactory outboxJPAQueryFactory;

    public DomainEventAdapter(DomainEventJpaRepository repository, DomainEventJpaMapper mapper, JPAQueryFactory outboxJPAQueryFactory) {
        this.repository = repository;
        this.mapper = mapper;
        this.outboxJPAQueryFactory = outboxJPAQueryFactory;
    }

    @Override
    @Transactional
    public void save(DomainEvent event) {
        repository.save(mapper.toEntity(event));
    }

    @Override
    public DomainEvent findById(Long id) {
        return mapper.toDomain(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void publishAll(List<Long> ids, LocalDateTime now) {
        outboxJPAQueryFactory.update(domainEventEntity)
                .where(domainEventEntity.id.in(ids))
                .set(domainEventEntity.published, true)
                .set(domainEventEntity.publishedAt, now)
                .execute();
    }
}
