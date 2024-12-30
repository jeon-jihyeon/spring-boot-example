package com.example.spring.domain.player.model;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.team.model.TeamId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest extends BaseUnitTest {
    @ParameterizedTest
    @CsvSource(value = {"first,last"})
    @DisplayName("Player 모델 초기화 테스트")
    void shouldInitializePlayerModel(String first, String last) {
        final Player model = Player.create(new PlayerCreateCommand(new FullName(first, last)));
        assertThat(model.getId().value()).isGreaterThan(0L);
        assertThat(model.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(model.getTeamId()).isEqualTo(TeamId.NoTeam);
        assertThat(model.getFullName().firstName()).isEqualTo(first);
        assertThat(model.getFullName().lastName()).isEqualTo(last);
    }

    @Test
    void shouldChangeTeamId() {
        final Player model = Player.create(new PlayerCreateCommand(new FullName("first", "last")));
        final TeamId teamId = new TeamId(12L);
        assertThat(model.joinTeam(teamId).getTeamId()).isEqualTo(teamId);
        assertThat(model.leaveTeam().getTeamId()).isEqualTo(TeamId.NoTeam);
    }
}