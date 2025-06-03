package com.starmix.checkmate.domain.notification;

import com.starmix.checkmate.domain.comment.Comment;
import com.starmix.checkmate.domain.meeting.Meeting;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Notification {
    private String notificationId;
    private String userId;
    private String title;
    private String description;
    private String targetId;
    private Boolean isRead;
    private Project project;

    public static Notification createProjectInviteNotification(String userId, Project project) {
        return Notification.builder()
                .userId(userId)
                .title("프로젝트 초대 요청이 도착했어요!")
                .description(project.getTitle() + " 에 초대되었어요")
                .targetId(project.getProjectId())
                .isRead(false)
                .project(project)
                .build();
    }

    public static Notification assignTaskNotification(String userId, Task task, Project project) {
        return Notification.builder()
                .userId(userId)
                .title("Task의 담당자로 할당되었어요!")
                .description(task.getTitle() + " 의 담당자로 할당되었어요!")
                .targetId(task.getTaskId())
                .isRead(false)
                .project(project)
                .build();
    }

    public static Notification commentOnTask(String userId, Task task, Comment comment, Project project) {
        return Notification.builder()
                .userId(userId)
                .title("Task에 새 댓글이 추가되었어요!")
                .description(comment.getAuthor().getName() + " 님이 " + task.getTitle() + " 에 댓글을 작성했어요!")
                .targetId(task.getTaskId())
                .isRead(false)
                .project(project)
                .build();
    }

    public static Notification updateMyProfile(String userId, Project project) {
        return Notification.builder()
                .userId(userId)
                .title("내 정보가 변경되었어요!")
                .description("프로젝트 리더가 내 정보를 변경했어요!")
                .targetId(project.getProjectId())
                .isRead(false)
                .project(project)
                .build();
    }

    public static Notification makeMeetingNotification(String userId, Meeting meeting, Project project) {
        return Notification.builder()
                .userId(userId)
                .title("회의록이 추가되었어요!")
                .description(meeting.getMaster() + " 님이 " + meeting.getTitle() + " 을 추가했어요!")
                .targetId(meeting.getMeetingId())
                .isRead(false)
                .project(project)
                .build();
    }

    public static Notification createSprintNotification(String userId, Sprint sprint, Project project) {
        return Notification.builder()
                .userId(userId)
                .title("스프린트가 추가되었어요!")
                .description(sprint.getTitle() + " 가 추가되었어요. 새로운 Task를 확인해보세요!")
                .targetId(project.getProjectId())
                .isRead(false)
                .project(project)
                .build();
    }

    public void read() {
        this.isRead = true;
    }
}
