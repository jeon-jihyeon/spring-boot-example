package com.example.spring.domain.player;

public interface PlayerCommandRepository {
    Player save(Player player);

    Player findById(PlayerId id);

    void deleteById(PlayerId id);
}
