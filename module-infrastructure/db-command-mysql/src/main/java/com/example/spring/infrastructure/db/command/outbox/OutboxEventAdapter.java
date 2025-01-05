package com.example.spring.infrastructure.db.command.outbox;

import com.example.spring.domain.event.DomainEventOutboxRepository;
import com.example.spring.domain.event.model.DomainEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Repository
public class OutboxEventAdapter implements DomainEventOutboxRepository {
    private static final String INSERT = "INSERT INTO outbox_events (id, completed, type, queue_name, model_id, created_at, completed_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final OutboxEventJpaRepository repository;
    private final JdbcTemplate jdbcTemplate;

    public OutboxEventAdapter(OutboxEventJpaRepository repository, JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void save(DomainEvent event) {
        repository.save(OutboxEventEntity.from(event));
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void createAll(List<DomainEvent> events) {
        jdbcTemplate.batchUpdate(INSERT, events, events.size(), (ps, e) -> {
            ps.setLong(1, e.id());
            ps.setBoolean(2, e.completed());
            ps.setString(3, e.type().name());
            ps.setString(4, e.queueName());
            ps.setLong(5, e.modelId());
            ps.setTimestamp(6, Timestamp.valueOf(e.createdAt()));
            ps.setTimestamp(7, Timestamp.valueOf(e.completedAt()));
        });
    }
}
