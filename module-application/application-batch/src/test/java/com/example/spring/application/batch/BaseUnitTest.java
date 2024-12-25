package com.example.spring.application.batch;

import org.junit.jupiter.api.Tag;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

@Tag("unit")
@SpringBatchTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public abstract class BaseUnitTest {
}
