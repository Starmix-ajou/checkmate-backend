package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.CommentEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.CommentMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.CommentMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.CommentPersistencePort;
import com.starmix.checkmate.domain.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CommentPersistenceAdapter implements CommentPersistencePort {

    private final CommentMongoRepository commentMongoRepository;

    @Override
    public List<Comment> findAllByTaskId(String taskId) {
        List<CommentEntity> commentEntities = commentMongoRepository.findAllByTaskId(taskId);
        return commentEntities.stream().map(CommentMapper::toDomain).toList();
    }
}
