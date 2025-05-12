package com.starmix.checkmate.adapter.out.redis;

import com.starmix.checkmate.adapter.out.redis.dto.SprintDetail;
import com.starmix.checkmate.domain.project.Project;
import lombok.Getter;

@Getter
public enum RedisType {
    PROJECT_INFO("PROJECT_INFO_", Project.class),
    SPRINT_INFO("SPRINT_INFO_", SprintDetail.class);

    private final String key;
    private final Class<?> type;

    RedisType(String key, Class<?> type) {
        this.key = key;
        this.type = type;
    }
}
