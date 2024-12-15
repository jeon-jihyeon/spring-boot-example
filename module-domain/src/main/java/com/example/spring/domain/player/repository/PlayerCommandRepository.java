package com.example.spring.domain.player.repository;

import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.PlayerId;

public interface PlayerCommandRepository {
    Player save(Player player);

    Player findById(PlayerId id);

    void deleteById(PlayerId id);
}
