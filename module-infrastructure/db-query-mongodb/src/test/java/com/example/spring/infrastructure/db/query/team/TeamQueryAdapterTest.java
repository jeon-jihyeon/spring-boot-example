package com.example.spring.infrastructure.db.query.team;

import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.infrastructure.db.query.BaseContextTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TeamQueryAdapterTest extends BaseContextTest {
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final TeamData TEAM_DATA = TeamData.of(22L, "name", NOW, List.of(11L));

    @Autowired
    private TeamQueryAdapter adapter;

    @Test
    void save() {
        adapter.save(TEAM_DATA);
        final TeamData found = adapter.findById(TEAM_DATA.id());
        assertThat(found.id().value()).isEqualTo(22L);
        assertThat(found.startsAt()).isEqualTo(NOW);
    }

    @Test
    void shouldFindTeamsAfter() {
        final LocalDateTime now = LocalDateTime.now();
        adapter.save(TeamData.of(1L, "name", now, List.of(11L)));
        adapter.save(TeamData.of(2L, "name", now, List.of(11L)));
        adapter.save(TeamData.of(3L, "name", now, List.of(11L)));

        final List<TeamData> teams = adapter.findTeamsAfter(now.minusSeconds(1));
        assertThat(teams.size()).isEqualTo(3);
        for (TeamData d : teams) {
            assertThat(d.id().value()).isIn(List.of(1L, 2L, 3L));
        }
    }
}