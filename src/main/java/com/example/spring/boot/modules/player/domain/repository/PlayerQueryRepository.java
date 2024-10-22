package com.example.spring.boot.modules.player.domain.repository;

import com.example.spring.boot.modules.player.domain.PlayerCondition;
import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerQueryRepository {
    Page<Player> findPlayers(PlayerCondition condition, Pageable pageable);

    Player findPlayer(PlayerId id);
}
