package com.starmix.checkmate.domain;

import com.starmix.checkmate.domain.comment.Comment;
import com.starmix.checkmate.domain.meeting.Meeting;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.domain.notification.NotificationType;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationTest {
    private String userId;
    private Project project;
    private User user;

    @BeforeEach
    void setUp() {
        userId = "user-123";
        project = Project.builder()
                .projectId("project-123")
                .title("테스트 프로젝트")
                .build();
        user = User.builder()
                .userId("user-123")
                .name("테스트 유저")
                .build();
    }

    @Test
    @DisplayName("프로젝트 초대 알림 생성 테스트")
    void createProjectInviteNotification() {
        // when
        Notification notification = Notification.createProjectInviteNotification(userId, project);

        // then
        assertNotificationCommonFields(notification, userId, project.getProjectId(), NotificationType.INVITE_PROJECT);
        assertThat(notification.getTitle()).isEqualTo("프로젝트 초대 요청이 도착했어요!");
        assertThat(notification.getDescription()).contains(project.getTitle());
    }

    @Test
    @DisplayName("Task 할당 알림 생성 테스트")
    void assignTaskNotification() {
        // given
        Task task = Task.builder()
                .taskId("task-123")
                .title("테스트 태스크")
                .build();

        // when
        Notification notification = Notification.assignTaskNotification(userId, task, project);

        // then
        assertNotificationCommonFields(notification, userId, task.getTaskId(), NotificationType.ASSIGN_TASK);
        assertThat(notification.getTitle()).isEqualTo("Task의 담당자로 할당되었어요!");
        assertThat(notification.getDescription()).contains(task.getTitle());
    }

    @Test
    @DisplayName("Task 댓글 알림 생성 테스트")
    void commentOnTask() {
        // given
        Task task = Task.builder()
                .taskId("task-123")
                .title("테스트 태스크")
                .build();

        Comment comment = Comment.builder()
                .author(user)
                .build();

        // when
        Notification notification = Notification.commentOnTask(userId, task, comment, project);

        // then
        assertNotificationCommonFields(notification, userId, task.getTaskId(), NotificationType.COMMENT_TASK);
        assertThat(notification.getTitle()).isEqualTo("Task에 새 댓글이 추가되었어요!");
        assertThat(notification.getDescription()).contains(user.getName(), task.getTitle());
    }

    @Test
    @DisplayName("프로필 업데이트 알림 생성 테스트")
    void updateMyProfile() {
        // when
        Notification notification = Notification.updateMyProfile(userId, project);

        // then
        assertNotificationCommonFields(notification, userId, project.getProjectId(), NotificationType.UPDATE_PROFILE);
        assertThat(notification.getTitle()).isEqualTo("내 정보가 변경되었어요!");
    }

    @Test
    @DisplayName("회의록 생성 알림 테스트")
    void makeMeetingNotification() {
        // given
        Meeting meeting = Meeting.builder()
                .meetingId("meeting-123")
                .title("테스트 회의")
                .master(user)
                .build();

        // when
        Notification notification = Notification.makeMeetingNotification(userId, meeting, project);

        // then
        assertNotificationCommonFields(notification, userId, meeting.getMeetingId(), NotificationType.MAKE_MEETING);
        assertThat(notification.getTitle()).isEqualTo("회의록이 추가되었어요!");
        assertThat(notification.getDescription()).contains(user.getName(), meeting.getTitle());
    }

    @Test
    @DisplayName("스프린트 생성 알림 테스트")
    void createSprintNotification() {
        // given
        Sprint sprint = Sprint.builder()
                .sprintId("sprint-123")
                .title("테스트 스프린트")
                .build();

        // when
        Notification notification = Notification.createSprintNotification(userId, sprint, project);

        // then
        assertNotificationCommonFields(notification, userId, project.getProjectId(), NotificationType.CREATE_SPRINT);
        assertThat(notification.getTitle()).isEqualTo("스프린트가 구성되었어요!");
        assertThat(notification.getDescription()).contains(sprint.getTitle());
    }

    @Test
    @DisplayName("프로젝트 초대 수락 알림 테스트")
    void approveNotification() {
        // when
        Notification notification = Notification.approveNotification(userId, project, user);

        // then
        assertNotificationCommonFields(notification, userId, project.getProjectId(), NotificationType.INVITE_APPROVE);
        assertThat(notification.getTitle()).isEqualTo("프로젝트 초대를 수락했어요!");
        assertThat(notification.getDescription()).contains(user.getName(), project.getTitle());
    }

    @Test
    @DisplayName("프로젝트 초대 거절 알림 테스트")
    void denyNotification() {
        // when
        Notification notification = Notification.denyNotification(userId, project, user);

        // then
        assertNotificationCommonFields(notification, userId, project.getProjectId(), NotificationType.INVITE_DENY);
        assertThat(notification.getTitle()).isEqualTo("프로젝트 초대를 거절했어요!");
        assertThat(notification.getDescription()).contains(user.getName(), project.getTitle());
    }

    @Test
    @DisplayName("알림 읽음 처리 테스트")
    void read() {
        // given
        Notification notification = Notification.builder()
                .isRead(false)
                .build();

        // when
        notification.read();

        // then
        assertThat(notification.getIsRead()).isTrue();
    }

    private void assertNotificationCommonFields(
            Notification notification,
            String expectedUserId,
            String expectedTargetId,
            NotificationType expectedType
    ) {
        assertThat(notification.getUserId()).isEqualTo(expectedUserId);
        assertThat(notification.getTargetId()).isEqualTo(expectedTargetId);
        assertThat(notification.getIsRead()).isFalse();
        assertThat(notification.getProject()).isEqualTo(project);
        assertThat(notification.getType()).isEqualTo(expectedType);
    }
}