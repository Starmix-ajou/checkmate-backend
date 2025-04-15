package com.starmix.checkmate.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "epics")
@Getter
@SuperBuilder
public class DailyScrumEntity extends BaseEntity {
    private final LocalDate timestamp;
    @DBRef
    private final List<TaskEntity> todoTasks;
    @DBRef
    private final List<TaskEntity> doneTasks;
    private final String projectId;
}