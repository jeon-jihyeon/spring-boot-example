package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.Team;
import com.example.spring.infrastructure.db.BaseContextTest;
import com.example.spring.infrastructure.db.EntityNotFoundException;
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
        final Team saved = adapter.save(Team.of(22L, "name", now, List.of(new PlayerId(11L))));
        assertThat(saved.getId().value()).isEqualTo(22L);

        final Team found = adapter.findById(saved.getId());
        assertThat(found.getId().value()).isEqualTo(22L);
        assertThat(found.getName().value()).isEqualTo("name");
        assertThat(found.getStartsAt()).isEqualTo(now);

        adapter.deleteById(saved.getId());
        assertThrows(EntityNotFoundException.class, () -> adapter.findById(saved.getId()));
    }
}