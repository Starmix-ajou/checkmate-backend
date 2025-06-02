package com.starmix.checkmate.domain.project;

import com.starmix.checkmate.adapter.in.common.ProfileDto;
import com.starmix.checkmate.adapter.in.sse.web.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.*;

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
    private User productManager;
    private String imageUrl;

    public static Project createTemporaryProject(
            CreateFeatureDefinitionRequest request,
            User leader, List<User> members
    ) {
        String projectId = UUID.randomUUID().toString();

        members.forEach(member -> {
            ProfileDto profile = request.members().stream()
                    .filter(userBrief -> userBrief.email().equals(member.getEmail()))
                    .map(CreateFeatureDefinitionRequest.UserBrief::profile)
                    .findFirst()
                    .orElseThrow(() -> new CustomException("Member Not Found", HttpStatus.BAD_REQUEST));
            member.addProfile(Profile.init(profile, projectId));
        });

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

    public void addMember(User member) {
        if (this.members.contains(member)) {
            throw new CustomException("Member Already Exists", HttpStatus.BAD_REQUEST);
        }
        List<User> existingMembers = new ArrayList<>(this.members);
        existingMembers.add(member);
        this.members = existingMembers;
    }

    public void deleteMember(User member) {
        if (!this.members.contains(member)) {
            throw new CustomException("Member Not Exists", HttpStatus.BAD_REQUEST);
        }
        List<User> existingMembers = new ArrayList<>(this.members);
        existingMembers.remove(member);
        this.members = existingMembers;
    }

    public void changeProductManager(User productManager) {
        this.productManager = productManager;
    }

    public Map<String, Context> toMailContext() {
        Map<String, Context> mailContextMap = new HashMap<>();
        this.members.forEach(member -> {
            if(!member.getUserId().equals(this.leader.getUserId())) {
                Context context = new Context();
                context.setVariable("memberName", member.getName());
                context.setVariable("projectName", this.title);
                context.setVariable("projectPeriod", String.format("%s ~ %s", startDate, endDate));
                context.setVariable("projectJoinLink", "https://checkmate.it.kr");

                mailContextMap.put(member.getEmail(), context);
            }
        });
        return mailContextMap;
    }

    public Map<String, Context> toMailContext(User user) {
        Map<String, Context> mailContextMap = new HashMap<>();
        Context context = new Context();
        context.setVariable("memberName", user.getName());
        context.setVariable("projectName", this.title);
        context.setVariable("projectPeriod", String.format("%s ~ %s", startDate, endDate));
        context.setVariable("projectJoinLink", "https://checkmate.it.kr");

        mailContextMap.put(user.getEmail(), context);
        return mailContextMap;
    }

    public Boolean isMember(User user) {
        List<User> members = new ArrayList<>(this.members);
        return members.contains(user);
    }

    public Boolean isLeader(User user) {
        return leader.equals(user);
    }

    public Boolean isProductManager(User user) {
        return productManager.equals(user);
    }

    public void update(
            String title, String description,
            LocalDate endDate, String imageUrl
    ) {
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
    }

    public boolean isArchived() {
        LocalDate today = LocalDate.now();
        return this.endDate != null && this.endDate.isBefore(today);
    }

    public boolean canManageMember(User user, User member) {
        return isLeader(user) && !user.equals(member);
    }
}
