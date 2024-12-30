package com.example.spring.domain.team.model;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamTest extends BaseUnitTest {
    @ParameterizedTest
    @ValueSource(strings = {"name"})
    @DisplayName("Team 모델 초기화 테스트")
    void shouldInitializeTeamModel(String name) {
        final Team model = Team.create(new TeamCreateCommand(new TeamName(name)));
        assertThat(model.getId().value()).isGreaterThan(0L);
        assertThat(model.getName().value()).isEqualTo(name);
        assertThat(model.getStartsAt()).isNotNull();
        assertThat(model.getPlayerIds()).isEmpty();
    }

    @Test
    void shouldAddPlayerIdAndRemovePlayerId() {
        PlayerId playerId = new PlayerId(1L);
        Team model = Team.create(new TeamCreateCommand(new TeamName("name")));
        assertThat(model.getPlayerIds()).isEmpty();

        model = model.addPlayer(playerId);
        assertThat(model.getPlayerIds()).isEqualTo(List.of(playerId));

        model = model.removePlayer(playerId);
        assertThat(model.getPlayerIds()).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenPlayerIdsIsEmpty() {
        PlayerId playerId = new PlayerId(1L);
        Team model = Team.create(new TeamCreateCommand(new TeamName("name")));
        assertThrows(EmptyPlayerIdsException.class, () -> model.removePlayer(playerId));
    }
}