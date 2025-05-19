package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.common.Stack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
@Document(collection = "features")
@NoArgsConstructor
public class FeatureEntity extends BaseEntity {
    private String featureId;
    private String name;
    private String useCase;
    private String input;
    private String output;
    private String preCondition;
    private String postCondition;
    private Integer expectedDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer difficulty;
    private Integer priority;
    private String projectId;
}