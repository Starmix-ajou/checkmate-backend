package com.starmix.checkmate.adapter.in.rest.common.objectStorge.response;

import com.starmix.checkmate.adapter.out.objectStorage.dto.PresignedUrlInfo;
import lombok.Builder;

import java.util.Date;

@Builder
public record GeneratePresignedUrlResponse(
        String presignedUrl,
        String url,
        Date expiredAt
) {
    public static GeneratePresignedUrlResponse from(PresignedUrlInfo presignedUrlInfo) {
        return GeneratePresignedUrlResponse.builder()
                .presignedUrl(presignedUrlInfo.presignedUrl())
                .url(presignedUrlInfo.url())
                .expiredAt(presignedUrlInfo.expiredAt())
                .build();
    }
}
