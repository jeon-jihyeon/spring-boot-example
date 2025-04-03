package com.example.spring.domain.query;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerQueryService {
    private final PlayerQueryRepository repository;

    public PlayerQueryService(PlayerQueryRepository repository) {
        this.repository = repository;
    }

    public List<PlayerQuery> findPlayers(PlayerQueryCondition condition) {
        return repository.findPlayersByTeamId(condition.teamId());
    }

    public PlayerQuery query(Long id) {
        return repository.findById(id);
    }
}
