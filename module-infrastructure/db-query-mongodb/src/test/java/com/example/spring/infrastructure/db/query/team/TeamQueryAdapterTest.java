package com.example.spring.infrastructure.db.query.team;

import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.infrastructure.db.query.BaseEmbeddedDbTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TeamQueryAdapterTest extends BaseEmbeddedDbTest {
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final TeamData TEAM_DATA = TeamData.of(22L, "name", NOW);

    @Autowired
    private TeamQueryAdapter adapter;

    void save() {
        adapter.create(TEAM_DATA);
        final TeamData found = adapter.findById(TEAM_DATA.id());
        assertThat(found.id().value()).isEqualTo(22L);
        assertThat(found.startsAt()).isEqualTo(NOW);
    }

    void shouldFindTeamsAfter() {
        final LocalDateTime now = LocalDateTime.now();
        adapter.create(TeamData.of(1L, "name", now));
        adapter.create(TeamData.of(2L, "name", now));
        adapter.create(TeamData.of(3L, "name", now));

        final List<TeamData> teams = adapter.findTeamsAfter(now.minusSeconds(1));
        assertThat(teams.size()).isEqualTo(3);
        for (TeamData d : teams) {
            assertThat(d.id().value()).isIn(List.of(1L, 2L, 3L));
        }
    }
}