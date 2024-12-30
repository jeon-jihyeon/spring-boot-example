package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.PlayerId;

public interface PlayerQueryRepository {
    PlayerData save(PlayerData player);

    PlayerData findById(PlayerId id);

    void deleteById(PlayerId id);
}
