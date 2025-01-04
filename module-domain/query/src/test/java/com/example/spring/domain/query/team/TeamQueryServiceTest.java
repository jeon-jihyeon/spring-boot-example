package com.example.spring.domain.query.team;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.command.team.TeamCommandApiClient;
import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.query.player.PlayerQueryApiClient;
import com.example.spring.domain.query.player.dto.PlayerQuery;
import com.example.spring.domain.query.team.dto.TeamQuery;
import com.example.spring.domain.query.team.dto.TeamQueryCondition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamQueryServiceTest extends BaseUnitTest {
    private static final TeamId TEAM_ID = new TeamId(1L);
    @Mock
    private TeamQueryRepository repository;
    @Mock
    private TeamCommandApiClient client;
    @Mock
    private PlayerQueryApiClient playerClient;
    @InjectMocks
    private TeamQueryService service;

    @Test
    void shouldNotSaveWhenClientCausesException() {
        when(client.findById(any(TeamId.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.save(TEAM_ID));

        verify(repository, never()).save(any());
        verify(client, times(1)).findById(any());
    }

    @Test
    void shouldFindTeamsWithPlayersGroupedByTeamId() {
        var now = LocalDateTime.now();
        var teams = List.of(TeamData.of(1L, "n", now),
                TeamData.of(2L, "n", now),
                TeamData.of(3L, "n", now));
        var players = List.of(PlayerData.of(1L, Grade.C, "f", "l", 1L),
                PlayerData.of(2L, Grade.C, "f", "l", 2L),
                PlayerData.of(3L, Grade.C, "f", "l", 2L),
                PlayerData.of(4L, Grade.C, "f", "l", 3L));
        when(repository.findTeamsAfter(any())).thenReturn(teams);
        when(playerClient.findAllByTeamIds(any())).thenReturn(players);

        var res = service.findTeams(new TeamQueryCondition(now));
        assertThat(res.size()).isEqualTo(3);
        for (TeamQuery q : res) {
            for (PlayerQuery pq : q.players()) {
                assertThat(q.id()).isEqualTo(pq.teamId());
            }
        }
    }
}