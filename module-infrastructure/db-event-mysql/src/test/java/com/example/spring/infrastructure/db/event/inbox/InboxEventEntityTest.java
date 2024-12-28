package com.example.spring.infrastructure.db.event.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventState;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.infrastructure.db.event.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InboxEventEntityTest extends BaseUnitTest {

    @Test
    @DisplayName("InboxEventEntity 생성 테스트")
    void shouldCreateEntity() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEvent model = new DomainEvent(1L, DomainEventState.CREATED, DomainEventType.CREATE, "model", 2L, now, now, now);
        final InboxEventEntity entity = InboxEventEntity.from(model);
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getState()).isEqualTo(DomainEventState.CREATED);
        assertThat(entity.getModelName()).isEqualTo("model");
        assertThat(entity.getModelId()).isEqualTo(2L);
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getProcessedAt()).isEqualTo(now);
        assertThat(entity.getCompletedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("InboxEventEntity 모델 변환 테스트")
    void shouldMapToDomainModel() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEvent model = InboxEventEntity.from(new DomainEvent(1L, DomainEventState.CREATED, DomainEventType.CREATE, "model", 2L, now, now, now)).toModel();
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.state()).isEqualTo(DomainEventState.CREATED);
        assertThat(model.modelName()).isEqualTo("model");
        assertThat(model.modelId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.processedAt()).isEqualTo(now);
        assertThat(model.completedAt()).isEqualTo(now);
    }
}