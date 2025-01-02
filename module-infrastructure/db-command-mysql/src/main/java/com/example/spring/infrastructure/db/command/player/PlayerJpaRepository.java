package com.example.spring.infrastructure.db.command.player;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, Long> {
    List<PlayerEntity> findAllByTeamId(Long teamId);
}
