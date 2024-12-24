package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import com.example.spring.infrastructure.db.command.BaseContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamCommandAdapterTest extends BaseContextTest {
    private final TeamCommandAdapter adapter;

    public TeamCommandAdapterTest(TeamCommandAdapter adapter) {
        this.adapter = adapter;
    }

    @Test
    @DisplayName("Team DB 생성-조회-삭제 테스트")
    void shouldBeSavedAndFoundAndDeleted() {
        final LocalDateTime now = LocalDateTime.now();
        final TeamData saved = adapter.save(TeamData.of(22L, "name", now, List.of(11L)));
        assertThat(saved.id().value()).isEqualTo(22L);

        final TeamData found = adapter.findById(saved.id());
        assertThat(found.id().value()).isEqualTo(22L);
        assertThat(found.name().value()).isEqualTo("name");
        assertThat(found.startsAt()).isEqualTo(now);
        assertThat(found.playerIds().get(0).value()).isEqualTo(1L);

        adapter.deleteById(saved.id());
        assertThrows(EntityNotFoundException.class, () -> adapter.findById(saved.id()));
    }
}