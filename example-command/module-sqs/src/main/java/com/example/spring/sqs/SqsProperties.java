package com.example.spring.sqs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

@ConfigurationProperties(prefix = "spring.cloud.aws.sqs")
public record SqsProperties(
        String endPoint,
        String region,
        String accessKey,
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
