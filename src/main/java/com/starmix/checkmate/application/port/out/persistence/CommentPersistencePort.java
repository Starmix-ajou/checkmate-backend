package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.comment.Comment;

import java.util.List;

public interface CommentPersistencePort {
    List<Comment> findAllByTaskId(String taskId);
}
