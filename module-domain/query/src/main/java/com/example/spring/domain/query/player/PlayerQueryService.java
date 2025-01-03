package com.example.spring.domain.query.player;

import com.example.spring.domain.command.player.PlayerCommandApiClient;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.query.player.dto.PlayerQuery;
import com.example.spring.domain.query.player.dto.PlayerQueryCondition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerQueryService {
    private final PlayerQueryRepository repository;
    private final PlayerCommandApiClient client;

    public PlayerQueryService(PlayerQueryRepository repository, PlayerCommandApiClient client) {
        this.repository = repository;
        this.client = client;
    }

    public void save(PlayerId playerId) {
        repository.save(client.findById(playerId));
    }

    public void delete(PlayerId playerId) {
        repository.deleteById(playerId);
    }

    public List<PlayerQuery> findPlayers(PlayerQueryCondition condition) {
        return repository.findAllByTeamIds(condition.teamIds()).stream().map(PlayerQuery::from).toList();
    }
}
