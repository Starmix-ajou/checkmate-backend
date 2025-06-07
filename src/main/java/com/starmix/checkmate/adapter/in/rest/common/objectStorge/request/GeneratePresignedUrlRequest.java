package com.starmix.checkmate.adapter.in.rest.common.objectStorge.request;

import com.starmix.checkmate.adapter.out.objectStorage.Bucket;

public record GeneratePresignedUrlRequest(
        Bucket bucket,
        String fileName
) { }
