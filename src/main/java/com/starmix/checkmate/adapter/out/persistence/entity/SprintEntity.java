package com.starmix.checkmate.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "sprints")
@Getter
@SuperBuilder
@NoArgsConstructor
public class SprintEntity extends BaseEntity {
    private String title;
    private String description;
    private Integer sequence;
    private String projectId;
    private LocalDate startDate;
    private LocalDate endDate;
}