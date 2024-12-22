package com.example.spring.infrastructure.db.inbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxEventJpaRepository extends JpaRepository<InboxEventEntity, Long> {
}
