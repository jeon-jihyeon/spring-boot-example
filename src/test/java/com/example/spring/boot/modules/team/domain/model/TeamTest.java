package com.example.spring.boot.modules.team.domain.model;

import com.example.spring.boot.modules.team.domain.repository.command.TeamCreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest {

    @ParameterizedTest
    @ValueSource(strings = {"name"})
    @DisplayName("Team 모델 초기화 검증")
    void create(String name) {
        final Team model = Team.create(new TeamCreateCommand(new TeamName(name)));
        assertThat(model.getId()).isNotNull();
        assertThat(model.getId().value()).isGreaterThan(0L);
        assertThat(model.getName().value()).isEqualTo(name);
        assertThat(model.getStartsAt()).isNotNull();
        assertThat(model.getPlayerIds()).isEmpty();
    }
}