package com.example.spring.api;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class ExTest {
    private AsyncExample async;
    private TransactionExample transaction;

    @Test
    void test() {

    }

    @Component
    public static class AsyncExample {
        private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExample.class);

        @Async
        public void run() {
        }
    }

    @Component
    public static class TransactionExample {
        private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExample.class);

        @Transactional
        public void run() {
        }
    }
}
