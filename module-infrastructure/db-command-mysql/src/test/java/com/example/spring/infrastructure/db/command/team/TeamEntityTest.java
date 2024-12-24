package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.infrastructure.db.command.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TeamEntityTest extends BaseUnitTest {

    @Test
    @DisplayName("TeamEntity 생성 테스트")
    void shouldCreateEntity() {
        final LocalDateTime now = LocalDateTime.now();
        final TeamEntity entity = TeamEntity.from(TeamData.of(22L, "name", now, List.of(1L)));
        assertThat(entity.getId()).isEqualTo(22L);
        assertThat(entity.getName()).isEqualTo("name");
        assertThat(entity.getStartsAt()).isEqualTo(now);
        assertThat(entity.getPlayerIds().stream().toList().get(0)).isEqualTo(1L);
    }

    @Test
    @DisplayName("TeamEntity 모델 변환 테스트")
    void shouldMapToDomainModel() {
        final LocalDateTime now = LocalDateTime.now();
        final TeamData data = TeamEntity.from(TeamData.of(22L, "name", now, List.of(1L))).toData();
        assertThat(data.id().value()).isEqualTo(22L);
        assertThat(data.name().value()).isEqualTo("name");
        assertThat(data.startsAt()).isEqualTo(now);
        assertThat(data.playerIds().get(0).value()).isEqualTo(1L);
    }
}