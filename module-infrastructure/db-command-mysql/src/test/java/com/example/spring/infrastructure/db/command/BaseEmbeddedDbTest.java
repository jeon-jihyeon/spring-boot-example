package com.example.spring.infrastructure.db.command;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@Tag("embedded-db")
@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BaseEmbeddedDbTest {
}
