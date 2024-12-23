package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;

public interface PlayerQueryRepository {
    PlayerData save(PlayerData player);
}
