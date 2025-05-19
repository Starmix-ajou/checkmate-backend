package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comments")
@Getter
@SuperBuilder
@NoArgsConstructor
public class CommentEntity extends BaseEntity {
    private String taskId;
    @DBRef
    private UserEntity author;
    private String message;
    private LocalDateTime timestamp;
    private Boolean isModified;
}