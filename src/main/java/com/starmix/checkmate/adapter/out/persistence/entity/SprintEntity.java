package com.starmix.checkmate.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "sprints")
@Getter
@SuperBuilder
public class SprintEntity extends BaseEntity {
    private String title;
    private String description;
    private Integer sequence;
    private String projectId;
    private LocalDate startDate;
    private LocalDate endDate;
}