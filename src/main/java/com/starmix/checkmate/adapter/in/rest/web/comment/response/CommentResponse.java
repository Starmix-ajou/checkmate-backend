package com.starmix.checkmate.adapter.in.rest.web.comment.response;

import com.starmix.checkmate.adapter.in.rest.common.UserDto;
import com.starmix.checkmate.domain.comment.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        String commentId,
        String taskId,
        UserDto author,
        String message,
        LocalDateTime timestamp,
        Boolean isModified
) {
    public static CommentResponse fromDomain(Comment comment, UserDto author) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .taskId(comment.getTaskId())
                .author(author)
                .message(comment.getMessage())
                .timestamp(comment.getTimestamp())
                .isModified(comment.getIsModified())
                .build();
    }
}
