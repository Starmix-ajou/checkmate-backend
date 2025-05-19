package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.CommentEntity;
import com.starmix.checkmate.domain.comment.Comment;

public class CommentMapper {

    public static Comment toDomain(CommentEntity entity) {
        return Comment.builder()
                .taskId(entity.getTaskId())
                .author(UserMapper.toDomain(entity.getAuthor()))
                .message(entity.getMessage())
                .commentId(entity.getId())
                .timestamp(entity.getTimestamp())
                .isModified(entity.getIsModified())
                .build();
    }

    public static CommentEntity toEntity(Comment domain) {
        return CommentEntity.builder()
                .taskId(domain.getTaskId())
                .author(UserMapper.toEntity(domain.getAuthor()))
                .message(domain.getMessage())
                .timestamp(domain.getTimestamp())
                .id(domain.getCommentId())
                .isModified(domain.getIsModified())
                .build();
    }
}
