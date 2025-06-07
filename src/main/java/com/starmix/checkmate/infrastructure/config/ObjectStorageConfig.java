package com.starmix.checkmate.infrastructure.config;

import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
@Getter
public class ObjectStorageConfig {

    @Value("${ncloud.endpoint}")
    private String endpoint;

    @Value("${ncloud.user}")
    private String user;

    @Value("${ncloud.password}")
    private String password;

    @Value("${ncloud.region}")
    private String region;

    private S3Presigner presigner;

    @Bean
    public S3Presigner presigner() {
        this.presigner = S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(initializeCredentialsProvider())
                .build();
        return this.presigner;
    }

    @PreDestroy
    public void close() {
        if (presigner != null) {
            presigner.close();
        }
    }

    private StaticCredentialsProvider initializeCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(
                this.user, this.password
        ));
    }
}
