package com.starmix.checkmate.domain.comment;

import com.starmix.checkmate.domain.Base;
import com.starmix.checkmate.domain.user.User;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Comment extends Base {
    private final String taskId;
    private final User author;
    private final String content;
}