package com.starmix.checkmate.adapter.out.cache;

import com.starmix.checkmate.domain.project.Project;
import lombok.Getter;

import java.util.List;

@Getter
public enum CacheType {
    PROJECT_INFO("프로젝트 생성 정보", "PROJECT_INFO_", Project.class),
    FEATURES("기능", "FEATURES_", List.class);

    private final String displayName;
    private final String key;
    private final Class<?> type;

    CacheType(String displayName, String key, Class<?> type) {
        this.displayName = displayName;
        this.key = key;
        this.type = type;
    }
}
