package com.starmix.checkmate.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "epics")
@Getter
@SuperBuilder
public class EpicEntity extends BaseEntity {
    private String name;
    private String description;
    private String projectId;
}