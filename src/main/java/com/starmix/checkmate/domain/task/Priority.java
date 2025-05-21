package com.starmix.checkmate.domain.task;

import lombok.Getter;

@Getter
public enum Priority {
    HIGH(300),
    MEDIUM(200),
    LOW(100);

    private final Integer priorityNum;

    Priority(Integer priorityNum) {
        this.priorityNum = priorityNum;
    }

    public static Priority getPriority(Integer priorityNum) {
        if (priorityNum == null) {
            throw new IllegalArgumentException("priorityNum cannot be null");
        }

        if (priorityNum <= 100) {
            return LOW;
        } else if (priorityNum <= 200) {
            return MEDIUM;
        } else {
            return HIGH;
        }
    }
}
