package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.rest.common.objectStorge.request.GeneratePresignedUrlRequest;
import com.starmix.checkmate.adapter.in.rest.common.objectStorge.response.GeneratePresignedUrlResponse;
import com.starmix.checkmate.adapter.out.objectStorage.dto.PresignedUrlInfo;
import com.starmix.checkmate.application.port.out.objectStorage.ObjectStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ObjectStorageService {

    private final ObjectStoragePort objectStoragePort;

    public GeneratePresignedUrlResponse generatePresignedUrl(GeneratePresignedUrlRequest request) {
        PresignedUrlInfo presignedUrlInfo = objectStoragePort.generatePresignedUrl(
                request.bucket(),
                request.fileName()
        );
        return GeneratePresignedUrlResponse.from(presignedUrlInfo);
    }
}
