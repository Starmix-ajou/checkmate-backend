package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.ProjectEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectMapperTest {

    @Test
    @DisplayName("ProjectEntity -> Project 도메인 변환 테스트")
    void toDomainTest() {
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

        // when
        Project project = ProjectMapper.toDomain(projectEntity);

        // then
        assertThat(project).isNotNull();
        assertThat(project.getProjectId()).isEqualTo(projectEntity.getId());
        assertThat(project.getTitle()).isEqualTo(projectEntity.getTitle());
        assertThat(project.getDescription()).isEqualTo(projectEntity.getDescription());
        assertThat(project.getMembers()).isNotNull();
        assertThat(project.getMembers()).hasSize(2);
        assertThat(project.getMembers().get(0).getUserId()).isEqualTo(member1.getId());
        assertThat(project.getMembers().get(1).getUserId()).isEqualTo(member2.getId());
    }

    @Test
    @DisplayName("Project 도메인 -> ProjectEntity 변환 테스트")
    void toEntityTest() {
        // given
        User member1 = User.builder()
                .userId("user-3")
                .name("사용자3")
                .build();

        User member2 = User.builder()
                .userId("user-4")
                .name("사용자4")
                .build();

        List<User> members = Arrays.asList(member1, member2);

        Project project = Project.builder()
                .projectId("project-456")
                .title("새 프로젝트")
                .description("새 프로젝트 설명")
                .members(members)
                .build();

        // when
        ProjectEntity projectEntity = ProjectMapper.toEntity(project);

        // then
        assertThat(projectEntity).isNotNull();
        assertThat(projectEntity.getId()).isEqualTo(project.getProjectId());
        assertThat(projectEntity.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectEntity.getDescription()).isEqualTo(project.getDescription());
        assertThat(projectEntity.getMembers()).isNotNull();
        assertThat(projectEntity.getMembers()).hasSize(2);
        assertThat(projectEntity.getMembers().get(0).getId()).isEqualTo(member1.getUserId());
        assertThat(projectEntity.getMembers().get(1).getId()).isEqualTo(member2.getUserId());
    }

    @Test
    @DisplayName("빈 멤버 리스트를 가진 프로젝트 변환 테스트")
    void emptyMembersTest() {
        // given
        ProjectEntity entityWithEmptyMembers = ProjectEntity.builder()
                .id("project-empty")
                .title("빈 멤버 프로젝트")
                .members(Collections.emptyList())
                .build();

        Project domainWithEmptyMembers = Project.builder()
                .projectId("project-empty-domain")
                .title("빈 멤버 도메인 프로젝트")
                .members(Collections.emptyList())
                .build();

        // when
        Project resultDomain = ProjectMapper.toDomain(entityWithEmptyMembers);
        ProjectEntity resultEntity = ProjectMapper.toEntity(domainWithEmptyMembers);

        // then
        assertThat(resultDomain.getMembers()).isNotNull().isEmpty();
        assertThat(resultEntity.getMembers()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("null 멤버 리스트를 가진 프로젝트 변환 테스트")
    void nullMembersTest() {
        // given
        ProjectEntity entityWithNullMembers = ProjectEntity.builder()
                .id("project-null")
                .title("Null 멤버 프로젝트")
                .members(null)
                .build();

        Project domainWithNullMembers = Project.builder()
                .projectId("project-null-domain")
                .title("Null 멤버 도메인 프로젝트")
                .members(null)
                .build();

        // then
        assertThatThrownBy(() -> ProjectMapper.toDomain(entityWithNullMembers))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> ProjectMapper.toEntity(domainWithNullMembers))
                .isInstanceOf(NullPointerException.class);

    }

    @Test
    @DisplayName("null 프로젝트 변환 테스트")
    void nullProjectTest() {
        // then
        assertThatThrownBy(() -> ProjectMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> ProjectMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}