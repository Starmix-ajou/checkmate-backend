package com.starmix.checkmate.adapter.out.ai.client;

import com.starmix.checkmate.adapter.out.ai.client.request.CreateFeatureDefinitionFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.request.CreateFeatureSpecificationFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.request.CreateSprintFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.request.FeedbackFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "aiFeignClient", url = "${ai.external.server.url}")
public interface AIFeignClient {
    @PostMapping("/project/definition")
    CreateFeatureDefinitionFeignResponse createFeatureDefinition(CreateFeatureDefinitionFeignRequest request);

    @PutMapping("/project/definition")
    FeedbackFeatureDefinitionFeignResponse feedbackFeatureDefinition(FeedbackFeignRequest request);

    @PostMapping("/project/specification")
    CreateFeatureSpecificationFeignResponse createFeatureSpecification(CreateFeatureSpecificationFeignRequest request);

    @PutMapping("/project/specification")
    FeedbackFeatureSpecificationFeignResponse feedbackFeatureSpecification(FeedbackFeignRequest request);

    @PostMapping("/sprint")
    CreateSprintFeignResponse createSprint(CreateSprintFeignRequest request);
}