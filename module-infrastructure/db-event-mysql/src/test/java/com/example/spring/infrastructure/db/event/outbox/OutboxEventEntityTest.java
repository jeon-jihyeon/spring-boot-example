package com.example.spring.infrastructure.db.event.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.Layer;
import com.example.spring.infrastructure.db.event.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OutboxEventEntityTest extends BaseUnitTest {

    @Test
    @DisplayName("OutboxEventEntity 생성 테스트")
    void shouldCreateEntity() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEvent model = new DomainEvent(1L, Layer.DOMAIN, DomainEvent.Type.CREATE, "model", 2L, false, now, now);
        final OutboxEventEntity entity = OutboxEventEntity.from(model);
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getLayer()).isEqualTo(Layer.DOMAIN);
        assertThat(entity.getModelName()).isEqualTo("model");
        assertThat(entity.getModelId()).isEqualTo(2L);
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getCompleted()).isFalse();
        assertThat(entity.getCompletedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("OutboxEventEntity 모델 변환 테스트")
    void shouldMapToDomainModel() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEvent model = OutboxEventEntity.from(new DomainEvent(1L, Layer.DOMAIN, DomainEvent.Type.CREATE, "model", 2L, false, now, now)).toModel();
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.layer()).isEqualTo(Layer.DOMAIN);
        assertThat(model.modelName()).isEqualTo("model");
        assertThat(model.modelId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.completed()).isFalse();
        assertThat(model.completedAt()).isEqualTo(now);
    }
}