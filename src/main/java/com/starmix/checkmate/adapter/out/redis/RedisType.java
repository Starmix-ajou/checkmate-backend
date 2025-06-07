package com.starmix.checkmate.adapter.out.redis;

import com.starmix.checkmate.adapter.out.redis.dto.SprintDetail;
import com.starmix.checkmate.domain.payment.Payment;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.task.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisType {
    PROJECT_INFO("PROJECT_INFO_", Project.class),
    SPRINT_INFO("SPRINT_INFO_", SprintDetail.class),
    MEETING_ACTION_ITEMS("MEETING_ACTION_ITEMS", Task.class),
    PAYMENT_INFO("PAYMENT_INFO_", Payment.class);

    private final String key;
    private final Class<?> type;
}
