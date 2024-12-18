package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.Team;
import com.example.spring.infrastructure.db.BaseContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TeamCommandMapperTest extends BaseContextTest {
    private final TeamCommandMapper mapper;

    public TeamCommandMapperTest(TeamCommandMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    @DisplayName("TeamEntity 엔티티-모델 맵핑 테스트")
    void shouldMapTeamEntityToDomainModel() {
        final LocalDateTime now = LocalDateTime.now();
        final Team model = mapper.toDomain(new TeamEntity(22L, "name", now, Set.of(1L)));
        assertThat(model.getId().value()).isEqualTo(22L);
        assertThat(model.getName().value()).isEqualTo("name");
        assertThat(model.getStartsAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("Team 모델-엔티티 맵핑 테스트")
    void shouldMapTeamModelToEntity() {
        final LocalDateTime now = LocalDateTime.now();
        final TeamEntity entity = mapper.toEntity(Team.of(22L, "name", now, List.of(new PlayerId(1L))));
        assertThat(entity.getId()).isEqualTo(22L);
        assertThat(entity.getName()).isEqualTo("name");
        assertThat(entity.getStartsAt()).isEqualTo(now);
    }
}