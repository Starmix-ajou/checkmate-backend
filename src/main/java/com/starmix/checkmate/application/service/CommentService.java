package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.rest.common.UserDto;
import com.starmix.checkmate.adapter.in.rest.web.comment.request.CommentRequest;
import com.starmix.checkmate.adapter.in.rest.web.comment.response.CommentResponse;
import com.starmix.checkmate.application.port.out.persistence.CommentPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.NotificationPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.domain.comment.Comment;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentPersistencePort commentPersistencePort;
    private final TaskPersistencePort taskPersistencePort;
    private final JwtUtil jwtUtil;
    private final ProjectPersistencePort projectPersistencePort;
    private final NotificationPersistencePort notificationPersistencePort;

    public List<CommentResponse> getCommentsByTaskId(String taskId) {
        Task task = taskPersistencePort.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND));
        List<Comment> comments = commentPersistencePort.findAllByTaskId(taskId);
        return comments.stream().map(comment -> CommentResponse.fromDomain(
                        comment, UserDto.fromDomain(comment.getAuthor(), task.getEpic().getProjectId())
                )).toList();
    }

    public void createComment(String taskId, CommentRequest request) {
        User user = jwtUtil.extractUser();
        Task task = taskPersistencePort.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND));
        Comment comment = Comment.create(taskId, user, request.message());
        commentPersistencePort.save(comment);

        Project project = projectPersistencePort.findById(task.getEpic().getProjectId())
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));

        Notification notification = Notification.commentOnTask(
                task.getAssignee().getUserId(), task, comment, project
        );
        notificationPersistencePort.save(notification);
    }

    public void updateComment(String commentId, CommentRequest request) {
        User user = jwtUtil.extractUser();
        Comment comment = commentPersistencePort.findById(commentId)
                .orElseThrow(() -> new CustomException("Comment not found", HttpStatus.NOT_FOUND));
        if(!comment.isAuthor(user)) {
            throw new CustomException("Permission Denied", HttpStatus.FORBIDDEN);
        }
        comment.updateMessage(request.message());
        commentPersistencePort.save(comment);
    }

    public void deleteComment(String commentId) {
        User user = jwtUtil.extractUser();
        Comment comment = commentPersistencePort.findById(commentId)
                .orElseThrow(() -> new CustomException("Comment not found", HttpStatus.NOT_FOUND));
        if(!comment.isAuthor(user)) {
            throw new CustomException("Permission Denied", HttpStatus.FORBIDDEN);
        }
        commentPersistencePort.delete(commentId);
    }
}
