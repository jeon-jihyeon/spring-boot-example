package com.example.spring.domain.player.repository;

import com.example.spring.domain.player.repository.condition.PlayerCondition;
import com.example.spring.domain.player.repository.query.PlayerListQuery;

import java.util.List;

public interface PlayerQueryRepository {
    List<PlayerListQuery> findPlayers(PlayerCondition condition);
}
