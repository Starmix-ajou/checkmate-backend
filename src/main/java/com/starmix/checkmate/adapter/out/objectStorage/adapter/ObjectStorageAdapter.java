package com.starmix.checkmate.adapter.out.objectStorage.adapter;

import com.starmix.checkmate.adapter.out.objectStorage.Bucket;
import com.starmix.checkmate.adapter.out.objectStorage.dto.PresignedUrlInfo;
import com.starmix.checkmate.application.port.out.objectStorage.ObjectStoragePort;
import com.starmix.checkmate.infrastructure.config.ObjectStorageConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ObjectStorageAdapter implements ObjectStoragePort {

    private final ObjectStorageConfig config;

    @Override
    public PresignedUrlInfo generatePresignedUrl(Bucket bucket, String filename) {
        try {
            Date expirationDate = Date.from(Instant.now().plus(10, ChronoUnit.MINUTES));
            String savedFilename = createFileName(filename);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket.getKey())
                    .key(savedFilename)
                    .contentType("multipart/form-data")
//                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(putObjectRequest)
                    .build();

            URL presignedUrl = config.getPresigner().presignPutObject(presignRequest).url();

            return PresignedUrlInfo.builder()
                    .presignedUrl(presignedUrl.toString())
                    .url("https://kr.object.ncloudstorage.com/" + bucket.getKey() + "/" + savedFilename)
                    .expiredAt(expirationDate)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate presigned URL", e);
        }
    }

    private String createFileName(String filename) {
        Integer random = new Random().nextInt(900000) + 100000;
        return random + "/" + filename;
    }
}
