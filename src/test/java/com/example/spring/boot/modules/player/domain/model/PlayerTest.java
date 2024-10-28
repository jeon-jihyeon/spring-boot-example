package com.example.spring.boot.modules.player.domain.model;

import com.example.spring.boot.modules.player.domain.command.CreatePlayerCommand;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {

    @ParameterizedTest
    @CsvSource(value = {"first,last"})
    void create(String first, String last) {
        final Player model = Player.create(new CreatePlayerCommand(new FullName(first, last)));
        assertThat(model.getId()).isNotNull();
        assertThat(model.getId().value()).isGreaterThan(0L);
        assertThat(model.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(model.getTeamId()).isEqualTo(TeamId.NoTeam);
        assertThat(model.getFullName().firstName()).isEqualTo(first);
        assertThat(model.getFullName().lastName()).isEqualTo(last);
    }
}