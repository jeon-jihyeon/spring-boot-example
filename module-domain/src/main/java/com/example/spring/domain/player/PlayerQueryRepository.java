package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.PlayerId;

public interface PlayerQueryRepository {
    PlayerData save(PlayerData player);

    void deleteById(PlayerId id);
}
