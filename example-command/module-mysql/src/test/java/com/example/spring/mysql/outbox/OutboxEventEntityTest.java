package com.example.spring.mysql.outbox;

import com.example.spring.domain.outbox.OutboxEvent;
import com.example.spring.domain.outbox.OutboxEventType;
import com.example.spring.mysql.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OutboxEventEntityTest extends BaseUnitTest {
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final OutboxEvent EVENT = new OutboxEvent(1L, false, OutboxEventType.PLAYER_CREATED, 2L, NOW, NOW);

    @Test
    @DisplayName("OutboxEventEntity 생성 테스트")
    void shouldCreateEntity() {
        final OutboxEventEntity entity = OutboxEventEntity.from(EVENT);
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getCompleted()).isFalse();
        assertThat(entity.getType()).isEqualTo(OutboxEventType.PLAYER_CREATED);
        assertThat(entity.getEntityId()).isEqualTo(2L);
        assertThat(entity.getCreatedAt()).isEqualTo(NOW);
        assertThat(entity.getCompletedAt()).isEqualTo(NOW);
    }

    @Test
    @DisplayName("OutboxEventEntity 모델 변환 테스트")
    void shouldMapToDomainModel() {
        final OutboxEvent model = OutboxEventEntity.from(EVENT).toModel();
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.completed()).isFalse();
        assertThat(model.type()).isEqualTo(OutboxEventType.PLAYER_CREATED);
        assertThat(model.entityId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(NOW);
        assertThat(model.completedAt()).isEqualTo(NOW);
    }
}