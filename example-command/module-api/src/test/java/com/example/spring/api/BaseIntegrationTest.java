package com.example.spring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

@Tag("integration")
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public abstract class BaseIntegrationTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
}
