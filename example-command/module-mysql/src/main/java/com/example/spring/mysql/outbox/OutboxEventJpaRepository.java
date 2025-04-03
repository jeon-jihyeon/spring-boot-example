package com.example.spring.mysql.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventJpaRepository extends JpaRepository<OutboxEventEntity, Long> {
}
