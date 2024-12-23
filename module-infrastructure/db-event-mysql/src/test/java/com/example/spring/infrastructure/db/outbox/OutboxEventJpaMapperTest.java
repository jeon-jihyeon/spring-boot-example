package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.infrastructure.db.BaseCommandDbTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OutboxEventJpaMapperTest extends BaseCommandDbTest {
    private final OutboxEventJpaMapper mapper;

    public OutboxEventJpaMapperTest(OutboxEventJpaMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    @DisplayName("OutboxEventEntity 엔티티-모델 맵핑 테스트")
    void shouldMapEntityToDomainModel() {
        final LocalDateTime now = LocalDateTime.now();
        final OutboxEventEntity entity = new OutboxEventEntity(1L, DomainEvent.Type.CREATE, "modelName", 2L, false, now, now);
        final DomainEvent model = mapper.toDomain(entity);
        assertThat(model.id()).isEqualTo(22L);
        assertThat(model.modelName()).isEqualTo("modelName");
        assertThat(model.modelId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.completed()).isFalse();
        assertThat(model.completedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("OutboxEvent 모델-엔티티 맵핑 테스트")
    void shouldMapModelToEntity() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEvent model = new DomainEvent(1L, DomainEvent.Type.CREATE, "modelName", 2L, false, now, now);
        final OutboxEventEntity entity = mapper.toEntity(model);
        assertThat(entity.getId()).isEqualTo(22L);
        assertThat(entity.getModelName()).isEqualTo("modelName");
        assertThat(entity.getModelId()).isEqualTo(2L);
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getCompleted()).isFalse();
        assertThat(entity.getCompletedAt()).isEqualTo(now);
    }
}