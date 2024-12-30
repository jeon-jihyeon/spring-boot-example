package com.example.spring.domain.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.TeamCommandApiClient;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.Team;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.domain.team.model.TeamName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerTeamServiceTest extends BaseUnitTest {
    private static final TeamId TEAM_ID = new TeamId(1L);
    private static final TeamData TEAM_DATA = TeamData.from(Team.create(new TeamCreateCommand(new TeamName("name"), List.of(new PlayerId(1L)))));
    @Mock
    private TeamCommandApiClient teamClient;
    @Mock
    private PlayerCommandService commandService;
    @InjectMocks
    private PlayerTeamService service;

    @Test
    void shouldNotRegisterWhenFindEventCausesException() {
        when(teamClient.findById(any(TeamId.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.handleCreate(TEAM_ID));

        verify(commandService, never()).registerAll(any());
        verify(teamClient, times(1)).findById(any());
    }

    @Test
    void shouldRunRegisterAllForCreate() {
        when(teamClient.findById(any(TeamId.class))).thenReturn(TEAM_DATA);
        service.handleCreate(TEAM_ID);

        verify(commandService, times(1)).registerAll(any());
        verify(teamClient, times(1)).findById(any());
    }

    @Test
    void shouldNotUpdateWhenFindEventCausesException() {
        when(teamClient.findById(any(TeamId.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.handleUpdate(TEAM_ID));

        verify(commandService, never()).registerAll(any());
        verify(teamClient, times(1)).findById(any());
    }

    @Test
    void shouldRunRegisterAllForUpdate() {
        when(teamClient.findById(any(TeamId.class))).thenReturn(TEAM_DATA);
        service.handleUpdate(TEAM_ID);

        verify(commandService, times(1)).registerAll(any());
        verify(teamClient, times(1)).findById(any());
    }
}