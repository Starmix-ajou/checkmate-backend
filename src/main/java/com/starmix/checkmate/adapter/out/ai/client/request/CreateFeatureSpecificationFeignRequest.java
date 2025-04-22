package com.starmix.checkmate.adapter.out.ai.client.request;

import lombok.Builder;

@Builder
public record CreateFeatureSpecificationFeignRequest(
        String email
) { }