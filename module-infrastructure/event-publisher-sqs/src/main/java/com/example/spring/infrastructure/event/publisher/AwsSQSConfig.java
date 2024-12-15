package com.example.spring.infrastructure.event.publisher;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AwsSQSConfig {
    @Value("spring.cloud.aws.stack.auto")
    private boolean STACK_AUTO;
    @Value("spring.cloud.aws.region.static")
    private String REGION;
    @Value("spring.cloud.aws.credentials.access-key")
    private String ACCESS_KEY;
    @Value("spring.cloud.aws.credentials.secret-key")
    private String SECRET_KEY;
    @Value("spring.cloud.aws.queue.name")
    private String QUEUE_NAME;

    @Primary
    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .credentialsProvider(() -> new AwsCredentialsValue(ACCESS_KEY, SECRET_KEY))
                .region(Region.of(REGION))
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate() {
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient()).configure(sqsTemplateOptions -> {
            sqsTemplateOptions.defaultQueue(QUEUE_NAME);
        }).build();
    }

    private record AwsCredentialsValue(String accessKey, String secretKey) implements AwsCredentials {
        @Override
        public String accessKeyId() {
            return accessKey;
        }

        @Override
        public String secretAccessKey() {
            return secretKey;
        }
    }
}
