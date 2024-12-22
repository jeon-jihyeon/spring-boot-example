package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.infrastructure.db.BaseContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DomainEventJpaMapperTest extends BaseContextTest {
    private final DomainEventJpaMapper mapper;

    public DomainEventJpaMapperTest(DomainEventJpaMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    @DisplayName("DomainEventEntity 엔티티-모델 맵핑 테스트")
    void shouldMapEntityToDomainModel() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEvent model = mapper.toDomain(new DomainEventEntity(1L, "modelName", 2L, now, false, now));
        assertThat(model.id()).isEqualTo(22L);
        assertThat(model.modelName()).isEqualTo("modelName");
        assertThat(model.modelId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.published()).isFalse();
        assertThat(model.publishedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("DomainEvent 모델-엔티티 맵핑 테스트")
    void shouldMapModelToEntity() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEventEntity entity = mapper.toEntity(new DomainEvent(1L, "modelName", 2L, now, false, now));
        assertThat(entity.getId()).isEqualTo(22L);
        assertThat(entity.getModel()).isEqualTo("modelName");
        assertThat(entity.getModelId()).isEqualTo(2L);
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getPublished()).isFalse();
        assertThat(entity.getPublishedAt()).isEqualTo(now);
    }
}