package com.example.spring.boot.modules.player.domain;

public interface PlayerCommandRepository {
    Long save(Player player);

    void deleteById(Long id);
}
