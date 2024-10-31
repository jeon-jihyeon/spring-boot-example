package com.example.spring.boot.modules.player.domain.repository;

import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.modules.player.domain.model.PlayerId;

public interface PlayerCommandRepository {
    Player findById(PlayerId id);

    Long save(Player player);

    void deleteById(PlayerId id);
}
