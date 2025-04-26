package com.starmix.checkmate.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "dailyScrums")
@Getter
@SuperBuilder
@NoArgsConstructor
public class DailyScrumEntity extends BaseEntity {
    private LocalDate timestamp;
    @DBRef
    private List<TaskEntity> todoTasks;
    @DBRef
    private List<TaskEntity> doneTasks;
    private String projectId;
}