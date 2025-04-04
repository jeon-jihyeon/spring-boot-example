package com.example.spring.sns;

import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

@ConfigurationProperties(prefix = "spring.cloud.aws.sns")
public record AwsSnsProperties(
        String topicArn,
        String typeKey,
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
