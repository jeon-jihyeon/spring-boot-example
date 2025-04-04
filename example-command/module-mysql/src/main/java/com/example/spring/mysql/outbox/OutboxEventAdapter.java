package com.example.spring.mysql.outbox;

import com.example.spring.domain.outbox.OutboxEvent;
import com.example.spring.domain.outbox.OutboxEventRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class OutboxEventAdapter implements OutboxEventRepository {
    private final OutboxEventJpaRepository repository;

    public OutboxEventAdapter(OutboxEventJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void save(OutboxEvent event) {
        repository.save(OutboxEventEntity.from(event));
    }
}
