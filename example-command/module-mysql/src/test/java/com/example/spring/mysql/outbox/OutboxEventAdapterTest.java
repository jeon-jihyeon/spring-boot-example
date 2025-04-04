package com.example.spring.mysql.outbox;

import com.example.spring.domain.outbox.OutboxEvent;
import com.example.spring.domain.outbox.OutboxEventType;
import com.example.spring.mysql.BaseEmbeddedDbTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OutboxEventAdapterTest extends BaseEmbeddedDbTest {
    private final OutboxEventAdapter adapter;
    private final OutboxEventJpaRepository repository;

    public OutboxEventAdapterTest(OutboxEventAdapter adapter, OutboxEventJpaRepository repository) {
        this.adapter = adapter;
        this.repository = repository;
    }

    @Test
    @DisplayName("OutboxEvent DB 생성-조회-삭제 테스트")
    void shouldBeSavedAndFound() {
        var event = OutboxEvent.of(OutboxEventType.CREATE_PLAYER, 2L);
        adapter.save(event);
        var found = repository.findById(event.id()).orElseThrow(RuntimeException::new);
        assertThat(found.getId()).isEqualTo(event.id());
        assertThat(found.getCompleted()).isFalse();
        assertThat(found.getType()).isEqualTo(OutboxEventType.CREATE_PLAYER);
        assertThat(found.getEntityId()).isEqualTo(2L);
    }
}