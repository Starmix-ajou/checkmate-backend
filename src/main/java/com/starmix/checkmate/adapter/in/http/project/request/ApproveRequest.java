package com.starmix.checkmate.adapter.in.http.project.request;

import com.starmix.checkmate.domain.common.Stack;

import java.util.List;

public record ApproveRequest(
        List<Stack> stacks,
        List<String> positions
) { }
