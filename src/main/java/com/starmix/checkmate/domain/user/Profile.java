package com.starmix.checkmate.domain.user;

import com.starmix.checkmate.domain.common.Stack;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Profile {
    private final List<Stack> stacks;
    private final List<String> positions;
    private final String projectId;
}