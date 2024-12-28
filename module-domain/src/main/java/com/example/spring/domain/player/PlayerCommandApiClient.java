package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.PlayerId;

public interface PlayerCommandApiClient {
    PlayerData findById(PlayerId id);
}
