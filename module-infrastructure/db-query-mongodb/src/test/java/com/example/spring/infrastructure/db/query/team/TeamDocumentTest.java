package com.example.spring.infrastructure.db.query.team;

import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.infrastructure.db.query.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TeamDocumentTest extends BaseUnitTest {
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final TeamData TEAM_DATA = TeamData.of(22L, "name", NOW);

    @Test
    @DisplayName("TeamDocument 생성 테스트")
    void shouldCreateEntity() {
        final TeamDocument entity = TeamDocument.create(TEAM_DATA);
        assertThat(entity.getId()).isEqualTo(22L);
        assertThat(entity.getName()).isEqualTo("name");
        assertThat(entity.getStartsAt()).isEqualTo(NOW);
    }

    @Test
    @DisplayName("TeamDocument 모델 변환 테스트")
    void shouldMapToDomainModel() {
        final TeamData data = TeamDocument.create(TEAM_DATA).toData();
        assertThat(data.id().value()).isEqualTo(22L);
        assertThat(data.name().value()).isEqualTo("name");
        assertThat(data.startsAt()).isEqualTo(NOW);
    }
}