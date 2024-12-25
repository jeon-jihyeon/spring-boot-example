package com.example.spring.application.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class OutboxBatchController {
    private final JobLauncher jobLauncher;
    private final Job job;

    public OutboxBatchController(JobLauncher jobLauncher, @Qualifier("outboxBatchJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/batch/outbox")
    public void run() throws Exception {
        jobLauncher.run(job, new JobParametersBuilder()
                .addLocalDateTime("now", LocalDateTime.now()).toJobParameters());
    }
}
