package com.example.spring.domain.command.team.model;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.team.dto.TeamCreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest extends BaseUnitTest {
    @ParameterizedTest
    @ValueSource(strings = {"name"})
    @DisplayName("Team 모델 초기화 테스트")
    void shouldInitializeTeamModel(String name) {
        final Team model = Team.create(new TeamCreateCommand(new TeamName(name)));
        assertThat(model.getId().value()).isGreaterThan(0L);
        assertThat(model.getName().value()).isEqualTo(name);
        assertThat(model.getStartsAt()).isNotNull();
    }
}