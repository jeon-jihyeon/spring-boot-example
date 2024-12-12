package com.example.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

class IdGeneratorTest {

    private static final int MILLION = 1000000;
    private static final int HUNDRED = 100;

    @Test
    @DisplayName("id는 유일한 값으로 생성됨을 검증")
    void shouldGenerateUniqueValueForNewId() {
        assertThat(LongStream.range(0, MILLION).map(l -> IdGenerator.newId()).distinct().count()).isEqualTo(MILLION);
    }

    @Test
    @DisplayName("백만개 id 생성이 50밀리초 이내에 생성됨을 검증")
    void shouldGenerateOneMillionIdsWithin50Milliseconds() {
        final long startAt = System.currentTimeMillis();
        assertThat(LongStream.range(0, MILLION).map(l -> IdGenerator.newId()).count()).isEqualTo(MILLION);
        assertThat(System.currentTimeMillis() - startAt).isLessThan(50);
    }

    @Test
    @DisplayName("100개의 스레드에서 동시에 TSID 생성 시, 중복이 발생하지 않음을 검증")
    void noDuplicateTsidWith100Threads() throws InterruptedException, ExecutionException {
        // given
        final CountDownLatch latch = new CountDownLatch(HUNDRED);
        final ExecutorService executor = Executors.newFixedThreadPool(HUNDRED);
        final Set<Long> set = Collections.synchronizedSet(new HashSet<>());

        // when
        for (int i = 0; i < HUNDRED; i++) {
            executor.submit(() -> {
                set.add(IdGenerator.newId());
            }).get();
            latch.countDown();
        }
        latch.await();
        executor.shutdown();

        // then
        assertThat(set.size()).isEqualTo(HUNDRED);
    }
}