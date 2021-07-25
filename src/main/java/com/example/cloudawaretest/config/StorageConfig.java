package com.example.cloudawaretest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public S3Client generateS3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, accessSecret);
        Region regionS3 = Region.of(region);

        S3Client s3 = S3Client.builder()
                .region(regionS3)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        return s3;
    }

}
