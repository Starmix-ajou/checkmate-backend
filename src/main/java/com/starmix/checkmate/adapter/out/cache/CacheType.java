package com.starmix.checkmate.adapter.out.cache;

import com.starmix.checkmate.domain.project.Project;
import lombok.Getter;

@Getter
public enum CacheType {
    PROJECT_INFO_WITH_DEF("기능정의서가 포함된 프로젝트 생성 정보", "PROJECT_INFO_WITH_DEF_", Project.class),
    PROJECT_INFO_WITHOUT_DEF("기능정의서가 포함되지 않은 프로젝트 생성 정보", "PROJECT_INFO_WITHOUT_DEF_", Project.class);

    private final String displayName;
    private final String key;
    private final Class<?> type;

    CacheType(String displayName, String key, Class<?> type) {
        this.displayName = displayName;
        this.key = key;
        this.type = type;
    }
}
