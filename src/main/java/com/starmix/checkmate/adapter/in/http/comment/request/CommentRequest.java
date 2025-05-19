package com.starmix.checkmate.adapter.in.http.comment.request;

import lombok.Builder;

@Builder
public record CommentRequest(
        String message
) { }
