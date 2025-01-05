package com.example.spring.domain.query.player;

import com.example.spring.domain.query.player.dto.PlayerQuery;
import com.example.spring.domain.query.player.dto.PlayerQueryCondition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerQueryService {
    private final PlayerQueryRepository repository;

    public PlayerQueryService(PlayerQueryRepository repository) {
        this.repository = repository;
    }

    public List<PlayerQuery> findPlayers(PlayerQueryCondition condition) {
        return repository.findAllByTeamIds(condition.teamIds()).stream().map(PlayerQuery::from).toList();
    }
}
