package com.example.spring.infrastructure.db.event.inbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxEventJpaRepository extends JpaRepository<InboxEventEntity, Long> {
}
