package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;

public interface PlayerCommandApiClient {
    PlayerData findById(PlayerId id);
}
