package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentPersistencePort {
    List<Comment> findAllByTaskId(String taskId);
    void save(Comment comment);
    Optional<Comment> findById(String id);
    void delete(String id);
}
