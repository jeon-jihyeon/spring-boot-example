package com.example.spring.mysql.player;

import com.example.spring.domain.command.model.Grade;
import com.example.spring.mysql.BaseEmbeddedDbTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

class RepositoryConcurrencyTest extends BaseEmbeddedDbTest {
    private static final int HUNDRED = 100;
    @Autowired
    private PlayerJpaRepository playerRepository;

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
