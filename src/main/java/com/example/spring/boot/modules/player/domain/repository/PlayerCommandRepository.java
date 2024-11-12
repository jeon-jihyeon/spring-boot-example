package com.example.spring.boot.modules.player.domain.repository;

import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.modules.player.domain.model.PlayerId;

public interface PlayerCommandRepository {
    PlayerId save(Player player);

    Player findById(PlayerId id);

    void deleteById(PlayerId id);
}
