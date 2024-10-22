package com.example.spring.boot.modules.player.domain.repository;

import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.modules.player.domain.model.PlayerId;

public interface PlayerCommandRepository {
    Long save(Player player);

    void delete(PlayerId id);
}
