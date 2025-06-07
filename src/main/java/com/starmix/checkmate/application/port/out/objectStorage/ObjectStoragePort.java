package com.starmix.checkmate.application.port.out.objectStorage;

import com.starmix.checkmate.adapter.out.objectStorage.Bucket;
import com.starmix.checkmate.adapter.out.objectStorage.dto.PresignedUrlInfo;

public interface ObjectStoragePort {
    PresignedUrlInfo generatePresignedUrl(Bucket bucket, String filename);
}
