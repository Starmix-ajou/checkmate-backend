package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.task.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "tasks")
@SuperBuilder
@Getter
@NoArgsConstructor
public class TaskEntity extends BaseEntity {
    private String title;
    private String description;
    private Status status;

    @DBRef
    private UserEntity assignee;

    private LocalDate startDate;
    private LocalDate endDate;

    private Integer priority;

    @DBRef
    private List<EpicEntity> epic;
}