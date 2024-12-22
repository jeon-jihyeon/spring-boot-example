package com.example.spring.infrastructure.event.publisher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

@Component
public record AwsProperties(
        @Value("${spring.cloud.aws.end-point.url}")
        String endPoint,
        @Value("${spring.cloud.aws.region.static}")
        String region,
        @Value("${spring.cloud.aws.credentials.access-key}")
        String accessKey,
        @Value("${spring.cloud.aws.credentials.secret-key}")
        String secretKey
) {
    public AwsCredentials toCredentials() {
        return new AwsCredentialsValue(accessKey, secretKey);
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
