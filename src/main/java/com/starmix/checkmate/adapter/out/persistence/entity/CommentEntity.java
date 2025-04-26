package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@Getter
@SuperBuilder
@NoArgsConstructor
public class CommentEntity extends BaseEntity {
    private String taskId;
    private User author;
    private String content;
}