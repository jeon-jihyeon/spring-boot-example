package com.example.spring.infrastructure.db.query;


import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@Tag("context")
@SpringBootTest
@ActiveProfiles("local")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public abstract class BaseContextTest {
}
