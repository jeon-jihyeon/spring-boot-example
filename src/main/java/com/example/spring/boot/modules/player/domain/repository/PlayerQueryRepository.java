package com.example.spring.boot.modules.player.domain.repository;

import com.example.spring.boot.modules.player.domain.condition.PlayerCondition;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.player.domain.query.PlayerListQuery;
import com.example.spring.boot.modules.player.domain.query.PlayerQuery;

import java.util.List;

public interface PlayerQueryRepository {
    List<PlayerListQuery> findPlayers(PlayerCondition condition);

    PlayerQuery findPlayer(PlayerId id);
}
