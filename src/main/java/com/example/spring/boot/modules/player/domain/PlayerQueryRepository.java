package com.example.spring.boot.modules.player.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerQueryRepository {
    Page<Player> findPlayers(PlayerCondition condition, Pageable pageable);

    Player findPlayer(Long id);
}
