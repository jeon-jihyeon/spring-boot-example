package com.example.spring.infrastructure.db.command.test;

import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.infrastructure.db.command.BaseEmbeddedDbTest;
import com.example.spring.infrastructure.db.command.player.PlayerEntity;
import com.example.spring.infrastructure.db.command.player.PlayerJpaRepository;
import com.example.spring.infrastructure.db.command.team.TeamJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

class RepositoryConcurrencyTest extends BaseEmbeddedDbTest {
    private static final int HUNDRED = 100;
    @Autowired
    private TeamJpaRepository teamRepository;
    @Autowired
    private PlayerJpaRepository playerRepository;
    @Autowired
    private EntityManager em;

    @Test
    void test() throws InterruptedException {
        var latch = new CountDownLatch(HUNDRED);
        var executor = Executors.newFixedThreadPool(HUNDRED);
        var now = LocalDateTime.now();
        playerRepository.save(new PlayerEntity(1L, Grade.C, 0, "f", "l", 1L));
        for (int i = 0; i < HUNDRED; i++) {
            executor.submit(() -> {
                try {
                    var m = playerRepository.findById(1L).orElseThrow();
                    // TODO
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
            latch.countDown();
        }

        latch.await();
        executor.shutdown();
    }
}
