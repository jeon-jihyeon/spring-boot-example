package com.example.spring.domain.team;

import com.example.spring.domain.player.PlayerCommandApiClient;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.Grade;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamPlayerCommand;
import com.example.spring.domain.team.model.TeamId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamCommandPlayerServiceTest {
    private static final PlayerId PLAYER_ID = new PlayerId(2L);
    private static final TeamId TEAM_ID = new TeamId(1L);
    private static final TeamData TEAM_DATA = TeamData.of(1L, "name", LocalDateTime.now(), List.of(2L));
    private static final PlayerData PLAYER_DATA = PlayerData.of(2L, Grade.C, "first", "last", 1L);
    private static final PlayerData PLAYER_NO_TEAM = PlayerData.of(2L, Grade.C, "first", "last", 0L);
    @Mock
    private TeamCommandRepository commandRepository;
    @Mock
    private PlayerCommandApiClient playerClient;
    @InjectMocks
    private TeamCommandPlayerService service;

    @Test
    void shouldAddPlayer() {
        when(playerClient.findById(any())).thenReturn(PLAYER_DATA);
        when(commandRepository.findByPlayerId(any())).thenReturn(Optional.of(TEAM_DATA));
        when(commandRepository.findById(any())).thenReturn(TEAM_DATA);
        when(commandRepository.save(any())).thenReturn(TEAM_DATA);

        service.invoke(new TeamPlayerCommand(PLAYER_ID));
        verify(commandRepository, times(2)).save(any());
    }

    @Test
    void shouldNotSaveWhenPlayerHasNoTeam() {
        when(playerClient.findById(any())).thenReturn(PLAYER_NO_TEAM);
        when(commandRepository.findByPlayerId(any())).thenReturn(Optional.empty());

        service.invoke(new TeamPlayerCommand(PLAYER_ID));
        verify(commandRepository, never()).save(any());
    }
}