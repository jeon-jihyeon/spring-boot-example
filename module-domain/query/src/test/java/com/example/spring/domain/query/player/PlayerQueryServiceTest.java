package com.example.spring.domain.query.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.query.player.dto.PlayerQueryCondition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerQueryServiceTest extends BaseUnitTest {
    @Mock
    private PlayerQueryRepository repository;
    @InjectMocks
    private PlayerQueryService service;


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