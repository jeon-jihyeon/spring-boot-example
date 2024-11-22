package com.example.spring.boot.persistence.team;

import com.example.spring.boot.BaseContextTest;
import com.example.spring.boot.core.exception.EntityNotFoundException;
import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
        final Team model = Team.of(22L, "name", now);
        final TeamId saved = adapter.save(model);
        assertThat(saved.value()).isEqualTo(22L);

        final Team found = adapter.findById(saved);
        assertThat(found.getId().value()).isEqualTo(22L);
        assertThat(found.getName().value()).isEqualTo("name");
        assertThat(found.getStartsAt()).isEqualTo(now);

        adapter.deleteById(saved);
        assertThrows(EntityNotFoundException.class, () -> adapter.findById(saved));
    }
}