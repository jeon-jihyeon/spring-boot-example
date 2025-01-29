package com.example.spring.domain.command.player.model;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.player.PlayerHasNoTeamException;
import com.example.spring.domain.command.player.dto.PlayerCreateCommand;
import com.example.spring.domain.command.team.model.TeamId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest extends BaseUnitTest {
    @ParameterizedTest
    @CsvSource(value = {"first,last"})
    @DisplayName("Player 모델 초기화 테스트")
    void shouldInitializePlayerModel(String first, String last) {
        final Player model = Player.create(new PlayerCreateCommand(new FullName(first, last)));
        assertThat(model.getId().value()).isGreaterThan(0L);
        assertThat(model.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(model.getPoint().value()).isEqualTo(0);
        assertThat(model.getTeamId()).isEqualTo(TeamId.NoTeam);
        assertThat(model.getFullName().firstName()).isEqualTo(first);
        assertThat(model.getFullName().lastName()).isEqualTo(last);
    }

    @Test
    void shouldChangeTeamId() {
        var created = Player.create(new PlayerCreateCommand(new FullName("first", "last")));
        var teamId = new TeamId(12L);
        var hasTeam = created.joinTeam(teamId);
        assertThat(hasTeam.getTeamId()).isEqualTo(teamId);
        assertThat(hasTeam.leaveTeam().getTeamId()).isEqualTo(TeamId.NoTeam);
    }

    @Test
    void shouldThrowExceptionWhenPlayerHasNoTeam() {
        assertThrows(PlayerHasNoTeamException.class, Player.create(new PlayerCreateCommand(new FullName("first", "last")))::leaveTeam);
    }
}