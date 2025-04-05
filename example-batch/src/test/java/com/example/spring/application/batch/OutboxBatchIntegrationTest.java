package com.example.spring.application.batch;

import com.example.spring.outbox.OutboxEvent;
import com.example.spring.outbox.OutboxEventBatchProducer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OutboxBatchIntegrationTest extends BaseEmbeddedDbTest {
    private final JobLauncherTestUtils jobLauncherTestUtils;
    private final JdbcTemplate jdbcTemplate;
    @SpyBean
    private OutboxEventProducerSpy playerProducer;

    public OutboxBatchIntegrationTest(JobLauncherTestUtils jobLauncherTestUtils, JdbcTemplate jdbcTemplate) {
        this.jobLauncherTestUtils = jobLauncherTestUtils;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    public void testJob(@Autowired Job job) throws Exception {
        jobLauncherTestUtils.setJob(job);
        final LocalDateTime now = LocalDateTime.now();
        final String insert = "INSERT INTO outbox_events VALUES (?, ?, 'CREATE', 'model', ?, NULL, NOW(), NOW())";
        final String select = "SELECT COUNT(*) FROM outbox_events WHERE completed=FALSE";
        final JobParameters params = new JobParametersBuilder().addLocalDateTime("now", now).toJobParameters();

        for (int i = 1; i < 11; i++) jdbcTemplate.update(insert, i, false, i);
        for (int i = 11; i < 21; i++) jdbcTemplate.update(insert, i, true, i);
        assertThat(jdbcTemplate.queryForObject(select, Integer.class)).isEqualTo(10);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(params);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jdbcTemplate.queryForObject(select, Integer.class)).isEqualTo(0);
    }

    private final static class OutboxEventProducerSpy implements OutboxEventBatchProducer {
        private final Logger log = LoggerFactory.getLogger(getClass());

        @Override
        public void sendBatch(List<OutboxEvent> events) {
            log.info("[Spy Message] {}", events);
        }
    }
}