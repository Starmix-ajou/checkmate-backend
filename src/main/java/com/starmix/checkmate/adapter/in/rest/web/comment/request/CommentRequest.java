package com.starmix.checkmate.adapter.in.rest.web.comment.request;

import lombok.Builder;

@Builder
public record CommentRequest(
        String message
) { }
