package com.example.spring.infrastructure.db.command.outbox;

import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.event.model.DomainEventType;
import com.example.spring.infrastructure.db.command.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OutboxEventEntityTest extends BaseUnitTest {
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final DomainEvent EVENT = new DomainEvent(1L, false, DomainEventType.CREATE, "model", 2L, NOW, NOW);

    @Test
    @DisplayName("OutboxEventEntity 생성 테스트")
    void shouldCreateEntity() {
        final OutboxEventEntity entity = OutboxEventEntity.from(EVENT);
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getCompleted()).isFalse();
        assertThat(entity.getQueueName()).isEqualTo("model");
        assertThat(entity.getModelId()).isEqualTo(2L);
        assertThat(entity.getCreatedAt()).isEqualTo(NOW);
        assertThat(entity.getCompletedAt()).isEqualTo(NOW);
    }

    @Test
    @DisplayName("OutboxEventEntity 모델 변환 테스트")
    void shouldMapToDomainModel() {
        final DomainEvent model = OutboxEventEntity.from(EVENT).toModel();
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.completed()).isFalse();
        assertThat(model.queueName()).isEqualTo("model");
        assertThat(model.modelId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(NOW);
        assertThat(model.completedAt()).isEqualTo(NOW);
    }
}