package com.starmix.checkmate.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@Getter
@SuperBuilder
public class CommentEntity extends BaseEntity {
    private String taskId;
    private String authorId;
    private String content;
}