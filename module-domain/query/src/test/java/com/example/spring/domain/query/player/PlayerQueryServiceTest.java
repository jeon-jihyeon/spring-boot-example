package com.example.spring.domain.query.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.player.PlayerCommandApiClient;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.query.player.dto.PlayerQueryCondition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerQueryServiceTest extends BaseUnitTest {
    private static final PlayerId PLAYER_ID = new PlayerId(1L);
    @Mock
    private PlayerQueryRepository repository;
    @Mock
    private PlayerCommandApiClient client;
    @InjectMocks
    private PlayerQueryService service;

    @Test
    void shouldNotSaveWhenClientCausesException() {
        when(client.findById(any(PlayerId.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.save(PLAYER_ID));

        verify(repository, never()).save(any());
        verify(client, times(1)).findById(any());
    }

    @Test
    void shouldFindPlayers() {
        var players = List.of(PlayerData.of(1L, Grade.C, "f", "l", 1L),
                PlayerData.of(2L, Grade.C, "f", "l", 2L),
                PlayerData.of(3L, Grade.C, "f", "l", 2L),
                PlayerData.of(4L, Grade.C, "f", "l", 3L));
        when(repository.findAllByTeamIds(any())).thenReturn(players);

        var res = service.findPlayers(new PlayerQueryCondition(List.of()));
        assertThat(res.size()).isEqualTo(4);
    }
}