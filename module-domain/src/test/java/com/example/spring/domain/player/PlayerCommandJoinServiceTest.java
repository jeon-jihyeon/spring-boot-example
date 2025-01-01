package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.dto.PlayerJoinCommand;
import com.example.spring.domain.player.dto.PlayerTeamEvent;
import com.example.spring.domain.player.model.Grade;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.TeamCommandApiClient;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerCommandJoinServiceTest {
    private static final PlayerJoinCommand JOIN_COMMAND = new PlayerJoinCommand(new PlayerId(1L), new TeamId(2L));
    private static final PlayerData DATA = PlayerData.of(1L, Grade.C, "first", "last", 2L);
    private static final TeamData TEAM_DATA = TeamData.of(2L, "name", LocalDateTime.now(), List.of(1L));
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private TeamCommandApiClient teamClient;
    @Mock
    private PlayerCommandRepository repository;
    @InjectMocks
    private PlayerCommandJoinService service;

    @Test
    void shouldJoinToTeamAndReturnValidResponse() {
        when(repository.findById(any(PlayerId.class))).thenReturn(DATA);
        when(repository.save(any(PlayerData.class))).thenReturn(DATA);
        when(teamClient.findById(any(TeamId.class))).thenReturn(TEAM_DATA);

        // then
        assertThat(service.invoke(JOIN_COMMAND)).isEqualTo(DATA);
        verify(teamClient, times(1)).findById(any());
        verify(publisher, times(1)).publishEvent(any(PlayerTeamEvent.class));
    }

    @Test
    void shouldNotSaveWhenExceptionOccurs() {
        when(repository.findById(any(PlayerId.class))).thenReturn(DATA);
        when(teamClient.findById(any(TeamId.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.invoke(JOIN_COMMAND));

        // then
        verify(repository, times(1)).findById(any());
        verify(teamClient, times(1)).findById(any());
        verify(repository, never()).save(any());
        verify(publisher, never()).publishEvent(any());
    }
}