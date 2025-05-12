package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.common.Stack;
import com.starmix.checkmate.domain.feature.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private List<Stack> stacks;
    private Time time;
    private Integer difficulty;
    private Integer priority;
    private String projectId;
}