package com.starmix.checkmate.domain.task;

import com.starmix.checkmate.domain.Base;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Comment extends Base {
    private final String authorId;
    private final String content;
}