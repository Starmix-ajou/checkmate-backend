package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.CommentEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.CommentMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.CommentMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.CommentPersistencePort;
import com.starmix.checkmate.domain.comment.Comment;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CommentPersistenceAdapter implements CommentPersistencePort {

    private final CommentMongoRepository commentMongoRepository;

    @Override
    public List<Comment> findAllByTaskId(String taskId) {
        try {
            List<CommentEntity> commentEntities = commentMongoRepository.findAllByTaskId(taskId);
            return commentEntities.stream().map(CommentMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void save(Comment comment) {
        try {
            commentMongoRepository.save(CommentMapper.toEntity(comment));
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Comment> findById(String id) {
        try {
            Optional<CommentEntity> commentEntity = commentMongoRepository.findById(id);
            return commentEntity.map(CommentMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(String id) {
        try {
            commentMongoRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
