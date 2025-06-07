package com.starmix.checkmate.adapter.out.objectStorage.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record PresignedUrlInfo(
        String presignedUrl,
        String url,
        Date expiredAt
) { }
