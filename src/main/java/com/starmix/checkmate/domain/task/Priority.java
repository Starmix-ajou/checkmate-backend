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
        for (Priority priority : Priority.values()) {
            if (priority.getPriorityNum().equals(priorityNum)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid priority number: " + priorityNum);
    }
}
