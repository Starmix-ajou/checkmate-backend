package com.starmix.checkmate.domain;

import com.starmix.checkmate.adapter.in.common.ProfileDto;
import com.starmix.checkmate.adapter.in.sse.web.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectTest {
    private Project project;
    private User leader;
    private User member;
    private User productManager;

    @BeforeEach
    void setUp() {
        leader = User.builder()
                .userId("leader-id")
                .email("leader@test.com")
                .name("리더")
                .build();

        member = User.builder()
                .userId("member-id")
                .email("member@test.com")
                .name("멤버")
                .build();

        productManager = User.builder()
                .userId("pm-id")
                .email("pm@test.com")
                .name("Product Manager")
                .build();

        List<User> members = new ArrayList<>();
        members.add(leader);
        members.add(member);

        project = Project.builder()
                .projectId("test-project-id")
                .title("테스트 프로젝트")
                .description("테스트 설명")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .leader(leader)
                .members(members)
                .build();
    }

    @Test
    @DisplayName("프로젝트 생성 테스트")
    void createTemporaryProject() {
        // given
        CreateFeatureDefinitionRequest request = mock(CreateFeatureDefinitionRequest.class);
        ProfileDto profileDto = mock(ProfileDto.class);
        CreateFeatureDefinitionRequest.UserBrief userBrief = mock(CreateFeatureDefinitionRequest.UserBrief.class);

        when(request.title()).thenReturn("새 프로젝트");
        when(request.description()).thenReturn("설명");
        when(request.startDate()).thenReturn(LocalDate.now());
        when(request.endDate()).thenReturn(LocalDate.now().plusDays(30));
        when(userBrief.email()).thenReturn(member.getEmail());
        when(userBrief.profile()).thenReturn(profileDto);

        List<CreateFeatureDefinitionRequest.UserBrief> userBriefs = new ArrayList<>();
        userBriefs.add(userBrief);
        when(request.members()).thenReturn(userBriefs);

        // when
        Project newProject = Project.createTemporaryProject(request, leader, List.of(member));

        // then
        assertThat(newProject.getTitle()).isEqualTo("새 프로젝트");
        assertThat(newProject.getLeader()).isEqualTo(leader);
        assertThat(newProject.getMembers()).contains(member);
    }

    @Test
    @DisplayName("멤버 추가 테스트")
    void addMember() {
        // given
        User newMember = User.builder()
                .userId("new-member")
                .email("new@test.com")
                .build();

        // when
        project.addMember(newMember);

        // then
        assertThat(project.getMembers()).contains(newMember);
    }

    @Test
    @DisplayName("이미 존재하는 멤버 추가시 예외 발생")
    void addExistingMember() {
        assertThatThrownBy(() -> project.addMember(member))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("Member Already Exists");
    }

    @Test
    @DisplayName("프로젝트 아카이브 상태 확인")
    void isArchived() {
        // given
        Project archivedProject = project.toBuilder()
                .endDate(LocalDate.now().minusDays(1))
                .build();

        // when & then
        assertThat(archivedProject.isArchived()).isTrue();
        assertThat(project.isArchived()).isFalse();
    }

    @Test
    @DisplayName("프로젝트 프리미엄 상태 확인")
    void isPremium() {
        // given
        project.addPayment("payment-id");

        // when & then
        assertThat(project.isPremium()).isTrue();
    }

    @Test
    @DisplayName("메일 컨텍스트 생성 테스트")
    void toMailContext() {
        // when
        Map<String, Context> mailContext = project.toMailContext();

        // then
        assertThat(mailContext).containsKey(member.getEmail());
        assertThat(mailContext).doesNotContainKey(leader.getEmail());
    }

    @Test
    @DisplayName("권한 확인 테스트")
    void checkPermissions() {
        assertThat(project.isLeader(leader)).isTrue();
        assertThat(project.isLeader(member)).isFalse();
        assertThat(project.isMember(member)).isTrue();
        assertThat(project.isProductManager(productManager)).isFalse();
    }
}