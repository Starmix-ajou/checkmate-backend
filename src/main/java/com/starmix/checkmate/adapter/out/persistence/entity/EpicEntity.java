package com.starmix.checkmate.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "epics")
@Getter
@SuperBuilder
@NoArgsConstructor
public class EpicEntity extends BaseEntity {
    private String title;
    private String description;
    private String projectId;
    private String featureId;
    private LocalDate startDate;
    private LocalDate endDate;
}