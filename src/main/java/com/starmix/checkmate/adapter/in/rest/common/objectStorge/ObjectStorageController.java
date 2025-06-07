package com.starmix.checkmate.adapter.in.rest.common.objectStorge;

import com.starmix.checkmate.adapter.in.rest.common.objectStorge.request.GeneratePresignedUrlRequest;
import com.starmix.checkmate.adapter.in.rest.common.objectStorge.response.GeneratePresignedUrlResponse;
import com.starmix.checkmate.application.service.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/object-storage")
public class ObjectStorageController {

    private final ObjectStorageService objectStorageService;

    @PostMapping("/presigned-url")
    public ResponseEntity<GeneratePresignedUrlResponse> generatePresignedUrl(@RequestBody GeneratePresignedUrlRequest request) {
        GeneratePresignedUrlResponse response = objectStorageService.generatePresignedUrl(request);
        return ResponseEntity.ok().body(response);
    }
}