package com.starmix.checkmate.domain.project;

import com.starmix.checkmate.adapter.in.common.UserDto;
import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
public class Project {
    private String projectId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<User> members;
    private User leader;
    private String imageUrl;

    public void createProject(User leader, List<User> members) {
        leader.addProfile(this.leader.getProfileByProjectId(this.projectId));
        leader.approve(this.projectId);

        members.forEach(member ->
                member.addProfile(this.leader.getProfileByProjectId(this.projectId))
        );
    }

    public static Project createTemporaryProject(
            CreateFeatureDefinitionRequest request,
            User leader, List<User> members
    ) {
        String projectId = UUID.randomUUID().toString();

        Map<String, UserDto> requestMemberMap = request.members().stream()
                .collect(Collectors.toMap(UserDto::email, Function.identity()));

        UserDto requestLeader = requestMemberMap.get(leader.getEmail());
        if (requestLeader == null) {
            throw new CustomException("Leader Not Found", HttpStatus.BAD_REQUEST);
        }

        leader.addProfile(Profile.init(requestLeader.profile(), projectId));

        for (User member : members) {
            UserDto requestMember = requestMemberMap.get(member.getEmail());
            if (requestMember == null) {
                throw new CustomException("Member Not Found", HttpStatus.BAD_REQUEST);
            }
            member.addProfile(Profile.init(requestMember.profile(), projectId));
        }

        return Project.builder()
                .projectId(projectId)
                .title(request.title())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .leader(leader)
                .members(members)
                .build();
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

    public Boolean isMember(User user) {
        List<User> members = new ArrayList<>(this.members);
        return members.contains(user);
    }
}