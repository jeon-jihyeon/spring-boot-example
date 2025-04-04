package com.example.spring.mongodb.inbox;

import com.example.spring.domain.event.InboxEventType;
import com.example.spring.mongodb.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InboxEventDocumentTest extends BaseUnitTest {
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    @DisplayName("InboxEventEntity 생성 테스트")
    void shouldCreateEntity() {
        var document = new InboxEventDocument(1L, false, InboxEventType.PLAYER_CREATED, 2L, NOW, NOW);
        assertThat(document.getId()).isEqualTo(1L);
        assertThat(document.getCompleted()).isFalse();
        assertThat(document.getType()).isEqualTo(InboxEventType.PLAYER_CREATED);
        assertThat(document.getEntityId()).isEqualTo(2L);
        assertThat(document.getCreatedAt()).isEqualTo(NOW);
        assertThat(document.getCompletedAt()).isEqualTo(NOW);
    }

    @Test
    @DisplayName("InboxEventEntity 모델 변환 테스트")
    void shouldMapToDomainModel() {
        var document = new InboxEventDocument(1L, false, InboxEventType.PLAYER_CREATED, 2L, NOW, NOW);
        var model = document.toModel();
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.completed()).isFalse();
        assertThat(model.type()).isEqualTo(InboxEventType.PLAYER_CREATED);
        assertThat(model.entityId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(NOW);
        assertThat(model.completedAt()).isEqualTo(NOW);
    }
}