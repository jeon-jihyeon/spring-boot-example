package com.example.spring.domain.player.repository;

import com.example.spring.domain.player.query.PlayerListQuery;
import com.example.spring.domain.player.repository.condition.PlayerCondition;

import java.util.List;

public interface PlayerQueryRepository {
    List<PlayerListQuery> findPlayers(PlayerCondition condition);
}
