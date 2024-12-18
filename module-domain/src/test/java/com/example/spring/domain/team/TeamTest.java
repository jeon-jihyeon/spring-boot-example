package com.example.spring.domain.team;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest {
    @ParameterizedTest
    @ValueSource(strings = {"name"})
    @DisplayName("Team 모델 초기화 테스트")
    void shouldInitializeTeamModel(String name) {
        final Team model = Team.create(new TeamCreateCommand(new TeamName(name), List.of(new PlayerId(1L))));
        assertThat(model.getId()).isNotNull();
        assertThat(model.getId().value()).isGreaterThan(0L);
        assertThat(model.getName().value()).isEqualTo(name);
        assertThat(model.getStartsAt()).isNotNull();
    }
}