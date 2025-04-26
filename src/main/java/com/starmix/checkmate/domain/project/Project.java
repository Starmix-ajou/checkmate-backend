package com.starmix.checkmate.domain.project;

import com.starmix.checkmate.domain.Base;
import com.starmix.checkmate.domain.common.Stack;
import com.starmix.checkmate.domain.user.User;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public Map<String, Context> toMailContext() {
        Map<String, Context> mailContextMap = new HashMap<>();
        this.members.forEach(member -> {
            Context context = new Context();
            context.setVariable("memberName", member.getName());
            context.setVariable("projectName", this.title);
            context.setVariable("projectPeriod", String.format("%s ~ %s", startDate, endDate));
            context.setVariable("projectJoinLink", "https://checkmate.it.kr");

            mailContextMap.put(member.getEmail(), context);
        });
        return mailContextMap;
    }
}