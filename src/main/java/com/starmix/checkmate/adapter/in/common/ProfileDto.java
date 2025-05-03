package com.starmix.checkmate.adapter.in.common;

import com.starmix.checkmate.domain.common.Stack;

import java.util.List;

public record ProfileDto (
    List<Stack> stacks,
    List<String> positions
) { }
