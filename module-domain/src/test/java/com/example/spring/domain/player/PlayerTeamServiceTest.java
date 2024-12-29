package com.example.spring.domain.player;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.TeamCommandApiClient;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamUpdateEvent;
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
class PlayerTeamServiceTest {
    private static final TeamCreateEvent CREATE_EVENT = new TeamCreateEvent(new TeamId(1L));
    private static final TeamUpdateEvent UPDATE_EVENT = new TeamUpdateEvent(new TeamId(1L));
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
        assertThrows(RuntimeException.class, () -> service.handle(CREATE_EVENT));

        verify(commandService, never()).registerAll(any());
        verify(teamClient, times(1)).findById(any());
    }

    @Test
    void shouldRunRegisterAllForCreate() {
        when(teamClient.findById(any(TeamId.class))).thenReturn(TEAM_DATA);
        service.handle(CREATE_EVENT);

        verify(commandService, times(1)).registerAll(any());
        verify(teamClient, times(1)).findById(any());
    }

    @Test
    void shouldNotUpdateWhenFindEventCausesException() {
        when(teamClient.findById(any(TeamId.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.handle(UPDATE_EVENT));

        verify(commandService, never()).registerAll(any());
        verify(teamClient, times(1)).findById(any());
    }

    @Test
    void shouldRunRegisterAllForUpdate() {
        when(teamClient.findById(any(TeamId.class))).thenReturn(TEAM_DATA);
        service.handle(UPDATE_EVENT);

        verify(commandService, times(1)).registerAll(any());
        verify(teamClient, times(1)).findById(any());
    }
}