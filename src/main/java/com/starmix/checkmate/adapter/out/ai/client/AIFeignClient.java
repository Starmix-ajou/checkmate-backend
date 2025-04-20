package com.starmix.checkmate.adapter.out.ai.client;

import com.starmix.checkmate.adapter.out.ai.client.request.CreateFeatureDefinitionFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.request.FeatureDefinitionFeedbackFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateFeatureDefinitionFeignResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.FeatureDefinitionFeedbackFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "aiFeignClient", url = "${ai.external.server.url}")
public interface AIFeignClient {
    @GetMapping("/project/definition")
    CreateFeatureDefinitionFeignResponse createFunctionDefinition(CreateFeatureDefinitionFeignRequest request);


    @GetMapping("/project/definition-feedback")
    FeatureDefinitionFeedbackFeignResponse featureDefinitionFeedback(FeatureDefinitionFeedbackFeignRequest request);
}