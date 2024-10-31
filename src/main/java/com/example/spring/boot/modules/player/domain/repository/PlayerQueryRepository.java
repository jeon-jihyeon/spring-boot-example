package com.example.spring.boot.modules.player.domain.repository;

import com.example.spring.boot.modules.player.domain.condition.PlayerCondition;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.player.domain.query.PlayerListQuery;
import com.example.spring.boot.modules.player.domain.query.PlayerQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerQueryRepository {
    Page<PlayerListQuery> findPlayers(PlayerCondition condition, Pageable pageable);

    PlayerQuery findPlayer(PlayerId id);
}
