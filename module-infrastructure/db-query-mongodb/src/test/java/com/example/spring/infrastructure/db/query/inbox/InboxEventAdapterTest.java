package com.example.spring.infrastructure.db.query.inbox;

import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.event.model.DomainEventType;
import com.example.spring.infrastructure.db.query.BaseEmbeddedDbTest;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InboxEventAdapterTest extends BaseEmbeddedDbTest {
    private final InboxEventAdapter adapter;
    private final InboxEventMongoRepository repository;

    public InboxEventAdapterTest(InboxEventAdapter adapter, InboxEventMongoRepository repository) {
        this.adapter = adapter;
        this.repository = repository;
    }

    @DisplayName("InboxEvent DB 생성-조회-삭제 테스트")
    void shouldBeSavedAndFound() {
        final LocalDateTime now = LocalDateTime.now();
        adapter.save(new DomainEvent(1L, false, DomainEventType.CREATE, "model", 2L, now, now));

        final InboxEventDocument found = repository.findById(1L).orElseThrow(RuntimeException::new);
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getCompleted()).isFalse();
        assertThat(found.getType()).isEqualTo(DomainEventType.CREATE);
        assertThat(found.getQueueName()).isEqualTo("model");
        assertThat(found.getModelId()).isEqualTo(2L);
    }
}