package com.example.spring.boot.modules.team.domain.model;

import com.example.spring.boot.modules.team.domain.command.TeamCreateCommand;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest {

    @ParameterizedTest
    @ValueSource(strings = {"name"})
    void create(String name) {
        final Team model = Team.create(new TeamCreateCommand(new TeamName(name)));
        assertThat(model.getId()).isNotNull();
        assertThat(model.getId().value()).isGreaterThan(0L);
        assertThat(model.getName().value()).isEqualTo(name);
        assertThat(model.getStartsAt()).isNotNull();
        assertThat(model.getPlayerIds()).isEmpty();
    }
}