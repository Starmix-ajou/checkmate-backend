package com.starmix.checkmate.domain.project;

import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", members=" + members +
                ", leader=" + leader.toString() +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}