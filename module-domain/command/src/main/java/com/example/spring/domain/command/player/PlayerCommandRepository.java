package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.PlayerId;

public interface PlayerCommandRepository {
    PlayerData save(PlayerData player);

    PlayerData findById(PlayerId id);

    void deleteById(PlayerId id);
}
