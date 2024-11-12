package com.example.spring.boot.persistence.player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, Long> {
}
