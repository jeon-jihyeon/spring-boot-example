package com.example.spring.boot.persistence.team;

import com.example.spring.boot.BaseContextTest;
import com.example.spring.boot.modules.team.domain.model.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
        final TeamEntity entity = new TeamEntity(22L, "name", now);
        final Team model = mapper.toDomain(entity);
        assertThat(model.getId().value()).isEqualTo(22L);
        assertThat(model.getName().value()).isEqualTo("name");
        assertThat(model.getStartsAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("Team 모델-엔티티 맵핑 테스트")
    void shouldMapTeamModelToEntity() {
        final LocalDateTime now = LocalDateTime.now();
        final Team model = Team.of(22L, "name", now);
        final TeamEntity entity = mapper.toEntity(model);
        assertThat(entity.getId()).isEqualTo(22L);
        assertThat(entity.getName()).isEqualTo("name");
        assertThat(entity.getStartsAt()).isEqualTo(now);
    }
}