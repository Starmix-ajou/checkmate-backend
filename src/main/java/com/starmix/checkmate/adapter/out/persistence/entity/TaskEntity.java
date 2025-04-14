package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Status;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "tasks")
@SuperBuilder
@Getter
public class TaskEntity extends BaseEntity {
    private String name;
    private String description;
    private Status status;

    @DBRef
    private UserEntity assignee;

    private LocalDate startDate;
    private LocalDate endDate;

    private Priority priority;

    @DBRef
    private List<CommentEntity> comments;
}