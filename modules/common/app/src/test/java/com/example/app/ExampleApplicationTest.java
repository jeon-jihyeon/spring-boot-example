package com.example.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
class ExampleApplicationTest {

    @Test
    @DisplayName("애플리케이션 컨텍스트 로드")
    void contextLoads() {
        // Spring Boot 애플리케이션 컨텍스트가 정상적으로 로드되는지 확인
    }

    @Test
    @DisplayName("main 메서드 실행")
    void main_runsWithoutException() {
        assertThatCode(() -> ExampleApplication.main(new String[]{}))
                .doesNotThrowAnyException();
    }
}
