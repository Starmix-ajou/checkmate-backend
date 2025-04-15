package com.starmix.checkmate.application.service;

import com.starmix.checkmate.application.port.out.persistence.CommentPersistencePort;
import com.starmix.checkmate.domain.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentPersistencePort commentPersistencePort;

    public List<Comment> getCommentsByTaskId(String taskId) {
        return commentPersistencePort.findAllByTaskId(taskId);
    }
}