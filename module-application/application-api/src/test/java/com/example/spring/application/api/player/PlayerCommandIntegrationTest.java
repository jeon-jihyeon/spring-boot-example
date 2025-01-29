package com.example.spring.application.api.player;

import com.example.spring.application.api.BaseIntegrationTest;
import com.example.spring.application.api.player.request.PlayerCreateRequest;
import com.example.spring.application.api.player.request.PlayerPointRequest;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerCommandIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private PlayerCommandController controller;

    @Test
    @DisplayName("포인트 변경 테스트")
    void shouldAddPointsSuccessfully() {
        int a = 1;
        assertThat(a).isEqualTo(1);
        assertArrayEquals(new int[]{1}, new int[]{a});

        var data = controller.create(new PlayerCreateRequest("f", "l")).data();
        controller.addPoint(data.id(), new PlayerPointRequest(111));
        assertThat(controller.findPlayer(data.id()).data().point()).isEqualTo(111);
        controller.deletePlayer(data.id());
        assertThrows(EntityNotFoundException.class, () -> controller.findPlayer(data.id()));
    }

    @Test
    @DisplayName("포인트 변경 동시성 테스트")
    void shouldAddPointsConcurrently() throws Exception {
        // given
        final int COUNT = 5;
        var executor = Executors.newFixedThreadPool(COUNT);
        var latch = new CountDownLatch(COUNT);
        var data = controller.create(new PlayerCreateRequest("f", "l")).data();

        // when
        for (int i = 0; i < COUNT; i++) {
            executor.submit(() -> {
                try {
                    controller.addPoint(data.id(), new PlayerPointRequest(100));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();

        // then
        assertThat(controller.findPlayer(data.id()).data().point()).isEqualTo(100 * COUNT);
        controller.deletePlayer(data.id());
        assertThrows(EntityNotFoundException.class, () -> controller.findPlayer(data.id()));
    }
}