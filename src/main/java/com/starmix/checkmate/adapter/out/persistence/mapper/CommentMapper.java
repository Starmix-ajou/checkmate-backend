package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.CommentEntity;
import com.starmix.checkmate.domain.comment.Comment;

import java.time.LocalDateTime;

public class CommentMapper {

    public static Comment toDomain(CommentEntity entity) {
        return Comment.builder()
                .taskId(entity.getTaskId())
                .author(entity.getAuthor())
                .content(entity.getContent())
                .commentId(entity.getId())
                .build();
    }

    public static CommentEntity toEntity(Comment domain) {
        return CommentEntity.builder()
                .taskId(domain.getTaskId())
                .author(domain.getAuthor())
                .content(domain.getContent())
                .build();
    }
}
