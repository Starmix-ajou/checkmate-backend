package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.NotificationEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.ProjectEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.domain.notification.NotificationType;
import com.starmix.checkmate.domain.project.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class NotificationMapperTest {

    @Test
    @DisplayName("NotificationEntity -> Notification 도메인 변환 테스트")
    void toDomainTest() {
        // given
        ProjectEntity projectEntity = ProjectEntity.builder()
                .id("project-123")
                .title("테스트 프로젝트")
                .build();
        
        Project mockProject = mock(Project.class);
        
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .id("notification-123")
                .userId("user-123")
                .title("테스트 알림")
                .description("알림 설명")
                .targetId("target-123")
                .isRead(false)
                .project(projectEntity)
                .build();

        try (MockedStatic<ProjectMapper> projectMapperMock = mockStatic(ProjectMapper.class)) {
            // when
            projectMapperMock.when(() -> ProjectMapper.toDomain(projectEntity)).thenReturn(mockProject);
            Notification notification = NotificationMapper.toDomain(notificationEntity);

            // then
            assertThat(notification).isNotNull();
            assertThat(notification.getNotificationId()).isEqualTo(notificationEntity.getId());
            assertThat(notification.getUserId()).isEqualTo(notificationEntity.getUserId());
            assertThat(notification.getTitle()).isEqualTo(notificationEntity.getTitle());
            assertThat(notification.getDescription()).isEqualTo(notificationEntity.getDescription());
            assertThat(notification.getTargetId()).isEqualTo(notificationEntity.getTargetId());
            assertThat(notification.getIsRead()).isEqualTo(notificationEntity.getIsRead());
            assertThat(notification.getProject()).isEqualTo(mockProject);
        }
    }

    @Test
    @DisplayName("Notification 도메인 -> NotificationEntity 변환 테스트")
    void toEntityTest() {
        // given
        Project project = Project.builder()
                .projectId("project-456")
                .title("새 프로젝트")
                .build();
        
        ProjectEntity mockProjectEntity = mock(ProjectEntity.class);
        
        Notification notification = Notification.builder()
                .notificationId("notification-456")
                .userId("user-456")
                .title("새 알림")
                .description("새 알림 설명")
                .targetId("target-456")
                .isRead(true)
                .project(project)
                .type(NotificationType.ASSIGN_TASK)
                .build();

        try (MockedStatic<ProjectMapper> projectMapperMock = mockStatic(ProjectMapper.class)) {
            // when
            projectMapperMock.when(() -> ProjectMapper.toEntity(project)).thenReturn(mockProjectEntity);
            NotificationEntity notificationEntity = NotificationMapper.toEntity(notification);

            // then
            assertThat(notificationEntity).isNotNull();
            assertThat(notificationEntity.getId()).isEqualTo(notification.getNotificationId());
            assertThat(notificationEntity.getUserId()).isEqualTo(notification.getUserId());
            assertThat(notificationEntity.getTitle()).isEqualTo(notification.getTitle());
            assertThat(notificationEntity.getDescription()).isEqualTo(notification.getDescription());
            assertThat(notificationEntity.getTargetId()).isEqualTo(notification.getTargetId());
            assertThat(notificationEntity.getIsRead()).isEqualTo(notification.getIsRead());
            assertThat(notificationEntity.getProject()).isEqualTo(mockProjectEntity);
        }
    }

    @Test
    @DisplayName("읽음 상태가 다른 알림 변환 테스트")
    void readStatusTest() {
        // given
        UserEntity member1 = UserEntity.builder()
                .id("user-1")
                .name("사용자1")
                .build();

        UserEntity member2 = UserEntity.builder()
                .id("user-2")
                .name("사용자2")
                .build();

        List<UserEntity> members = Arrays.asList(member1, member2);

        ProjectEntity projectEntity = ProjectEntity.builder()
                .id("project-123")
                .title("테스트 프로젝트")
                .description("프로젝트 설명")
                .members(members)
                .build();

        NotificationEntity unreadEntity = NotificationEntity.builder()
                .id("notification-unread")
                .isRead(false)
                .title("알림 제목")
                .description("알림 내용")
                .project(projectEntity)
                .build();
        
        NotificationEntity readEntity = NotificationEntity.builder()
                .id("notification-read")
                .isRead(true)
                .title("알림 제목")
                .description("알림 내용")
                .project(projectEntity)
                .build();

        // when
        Notification unreadNotification = NotificationMapper.toDomain(unreadEntity);
        Notification readNotification = NotificationMapper.toDomain(readEntity);

        // then
        assertThat(unreadNotification.getIsRead()).isFalse();
        assertThat(readNotification.getIsRead()).isTrue();
    }

    @Test
    @DisplayName("알림 타입 정보 변환 테스트")
    void notificationTypeTest() {
        // given
        Project project = Project.builder().projectId("project-123").build();
        ProjectEntity mockProjectEntity = mock(ProjectEntity.class);

        Notification inviteNotification = Notification.builder()
                .notificationId("notification-invite")
                .type(NotificationType.INVITE_PROJECT)
                .project(project)
                .build();

        Notification taskNotification = Notification.builder()
                .notificationId("notification-task")
                .type(NotificationType.ASSIGN_TASK)
                .project(project)
                .build();

        try (MockedStatic<ProjectMapper> projectMapperMock = mockStatic(ProjectMapper.class)) {
            // when
            projectMapperMock.when(() -> ProjectMapper.toEntity(project)).thenReturn(mockProjectEntity);
            NotificationEntity inviteEntity = NotificationMapper.toEntity(inviteNotification);
            NotificationEntity taskEntity = NotificationMapper.toEntity(taskNotification);

            // then
            projectMapperMock.when(() -> ProjectMapper.toDomain(mockProjectEntity)).thenReturn(project);
            Notification resultInvite = NotificationMapper.toDomain(inviteEntity);
            Notification resultTask = NotificationMapper.toDomain(taskEntity);
        }
    }
    
    @Test
    @DisplayName("null 알림 변환 테스트")
    void nullNotificationTest() {
        // then
        assertThatThrownBy(() -> NotificationMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> NotificationMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}