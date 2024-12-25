package com.example.spring.application.batch;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventProducer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OutboxBatchIntegrationTest extends BaseContextTest {
    private final JobLauncherTestUtils jobLauncherTestUtils;
    private final JdbcTemplate jdbcTemplate;
    @SpyBean
    private TestProducer producer;

    public OutboxBatchIntegrationTest(JobLauncherTestUtils jobLauncherTestUtils, JdbcTemplate jdbcTemplate) {
        this.jobLauncherTestUtils = jobLauncherTestUtils;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    public void testJob(@Autowired Job job) throws Exception {
        jobLauncherTestUtils.setJob(job);
        final String insert = "INSERT INTO outbox_events VALUES (?, 'DOMAIN', 'CREATE', 'model', ?, FALSE, NULL, now(), now())";
        final String select = "SELECT COUNT(*) FROM outbox_events WHERE completed=FALSE";

        for (int i = 1; i <= 10; i++) jdbcTemplate.update(insert, i, i);
        // fixme
        assertThat(jdbcTemplate.queryForObject(select, Integer.class)).isEqualTo(10);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
        assertThat(jdbcTemplate.queryForObject(select, Integer.class)).isEqualTo(0);
    }

    private final static class TestProducer implements DomainEventProducer {
        private final Logger log = LoggerFactory.getLogger(getClass());

        @Override
        public void send(DomainEvent event) {
            log.info("[Spy Message] {}", event);
        }

        @Override
        public void sendBatch(List<DomainEvent> events) {
            log.info("[Spy Message] {}", events);
        }
    }
}