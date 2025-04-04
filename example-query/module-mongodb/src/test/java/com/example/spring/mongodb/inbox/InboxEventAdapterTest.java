package com.example.spring.mongodb.inbox;

import com.example.spring.domain.event.InboxEvent;
import com.example.spring.domain.event.InboxEventType;
import com.example.spring.mongodb.BaseEmbeddedDbTest;
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
        adapter.save(new InboxEvent(1L, false, InboxEventType.PLAYER_CREATED, 2L, now, now));

        final InboxEventDocument found = repository.findById(1L).orElseThrow(RuntimeException::new);
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getCompleted()).isFalse();
        assertThat(found.getType()).isEqualTo(InboxEventType.PLAYER_CREATED);
        assertThat(found.getEntityId()).isEqualTo(2L);
        assertThat(found.getCreatedAt()).isEqualTo(now);
        assertThat(found.getCompletedAt()).isEqualTo(now);
    }
}