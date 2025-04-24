package com.starmix.checkmate.domain.project;

import com.starmix.checkmate.domain.Base;
import com.starmix.checkmate.domain.common.Stack;
import com.starmix.checkmate.domain.user.User;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
public class Project extends Base {
    private final String title;
    private String description;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<Stack> stacks;
    private final List<User> members;
    private final User leader;

    public void approve(User user) {
        this.members.add(user);
    }
}