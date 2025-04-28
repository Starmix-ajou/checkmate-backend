package com.starmix.checkmate.domain.comment;

import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class Comment {
    private String commentId;
    private String taskId;
    private User author;
    private String content;
}