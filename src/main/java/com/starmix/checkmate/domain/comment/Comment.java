package com.starmix.checkmate.domain.comment;

import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
public class Comment {
    private String commentId;
    private String taskId;
    private User author;
    private String message;
    private LocalDateTime timestamp;
    private Boolean isModified;

    public static Comment create(String taskId, User author, String message) {
        return Comment.builder()
                .taskId(taskId)
                .author(author)
                .message(message)
                .timestamp(LocalDateTime.now())
                .isModified(false)
                .build();
    }

    public void updateMessage(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.isModified = true;
    }

    public boolean isAuthor(User user) {
        return this.author.equals(user);
    }
}
