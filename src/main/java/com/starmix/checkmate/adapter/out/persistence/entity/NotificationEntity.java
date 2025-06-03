package com.starmix.checkmate.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@SuperBuilder
@Document(collection = "notifications")
@NoArgsConstructor
public class NotificationEntity extends BaseEntity {
    private String userId;
    private String title;
    private String description;
    private String targetId;
    private Boolean isRead;
    @DBRef
    private ProjectEntity project;
}