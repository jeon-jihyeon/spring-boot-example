package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.PlayerId;

public interface PlayerCommandApiClient {
    PlayerData findById(PlayerId id);
}
