package com.example.spring.domain.query.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.query.PlayerQuery;
import com.example.spring.domain.query.PlayerQueryCondition;
import com.example.spring.domain.query.PlayerQueryRepository;
import com.example.spring.domain.query.PlayerQueryService;
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
        var players = List.of(new PlayerQuery(1L, "C", 0, "f", "l", 1L),
                new PlayerQuery(2L, "C", 0, "f", "l", 2L),
                new PlayerQuery(3L, "C", 0, "f", "l", 2L),
                new PlayerQuery(4L, "C", 0, "f", "l", 3L));
        when(repository.findPlayersByTeamId(any())).thenReturn(players);

        var res = service.findPlayers(new PlayerQueryCondition(0L));
        assertThat(res.size()).isEqualTo(4);
    }
}