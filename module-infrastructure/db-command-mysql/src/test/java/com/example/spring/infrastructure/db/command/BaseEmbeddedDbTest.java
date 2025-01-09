package com.example.spring.infrastructure.db.command;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@Tag("embedded-db")
@DataJpaTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BaseEmbeddedDbTest {
}
