package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.player.model.PlayerId;
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
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final Long[] PLAYER_IDS = {11L, 22L, 33L};
    private static final TeamData TEAM_DATA = TeamData.of(22L, "name", NOW, List.of(PLAYER_IDS));
    private final TeamCommandAdapter adapter;

    public TeamCommandAdapterTest(TeamCommandAdapter adapter) {
        this.adapter = adapter;
    }

    @Test
    @DisplayName("Team DB 생성-조회-삭제 테스트")
    void shouldBeSavedAndFoundAndDeleted() {
        final TeamData saved = adapter.save(TEAM_DATA);
        assertThat(saved.id().value()).isEqualTo(22L);

        final TeamData found = adapter.findById(saved.id());
        assertThat(found.id().value()).isEqualTo(22L);
        assertThat(found.name().value()).isEqualTo("name");
        assertThat(found.startsAt()).isEqualTo(NOW);
        assertThat(found.playerIds().get(0).value()).isEqualTo(11L);

        adapter.deleteById(saved.id());
        assertThrows(EntityNotFoundException.class, () -> adapter.findById(saved.id()));
    }

    @Test
    void shouldFindByPlayerId() {
        final TeamData saved = adapter.save(TEAM_DATA);
        assertThat(saved.playerIds().size()).isEqualTo(3);

        TeamData found = adapter.findByPlayerId(new PlayerId(33L)).orElseThrow();
        assertThat(found.id()).isEqualTo(saved.id());

        found = adapter.findByPlayerId(new PlayerId(22L)).orElseThrow();
        assertThat(found.id()).isEqualTo(saved.id());
    }
}