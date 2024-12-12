package com.example.spring.domain.player;

import com.example.spring.domain.player.repository.command.PlayerCreateCommand;
import com.example.spring.domain.team.TeamId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {
    @ParameterizedTest
    @CsvSource(value = {"first,last"})
    @DisplayName("Player 모델 초기화 테스트")
    void shouldInitializePlayerModel(String first, String last) {
        final Player model = Player.create(new PlayerCreateCommand(new FullName(first, last)));
        assertThat(model.getId()).isNotNull();
        assertThat(model.getId().value()).isGreaterThan(0L);
        assertThat(model.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(model.getTeamId()).isEqualTo(TeamId.NoTeam);
        assertThat(model.getFullName().firstName()).isEqualTo(first);
        assertThat(model.getFullName().lastName()).isEqualTo(last);
    }
}