package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.MeetingEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.meeting.Meeting;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class MeetingMapperTest {

    @Test
    @DisplayName("MeetingEntity -> Meeting 도메인 변환 테스트")
    void toDomainTest() {
        // given
        LocalDate today = LocalDate.now();
        UserEntity masterEntity = UserEntity.builder()
                .id("user-123")
                .name("홍길동")
                .build();
        
        User mockUser = mock(User.class);
        
        MeetingEntity meetingEntity = MeetingEntity.builder()
                .id("meeting-123")
                .title("6월 15일의 회의록")
                .content("회의 내용입니다.")
                .master(masterEntity)
                .projectId("project-123")
                .timestamp(today)
                .summary("회의 요약입니다.")
                .build();

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {
            // when
            userMapperMock.when(() -> UserMapper.toDomain(masterEntity)).thenReturn(mockUser);
            Meeting meeting = MeetingMapper.toDomain(meetingEntity);

            // then
            assertThat(meeting).isNotNull();
            assertThat(meeting.getMeetingId()).isEqualTo(meetingEntity.getId());
            assertThat(meeting.getTitle()).isEqualTo(meetingEntity.getTitle());
            assertThat(meeting.getContent()).isEqualTo(meetingEntity.getContent());
            assertThat(meeting.getMaster()).isEqualTo(mockUser);
            assertThat(meeting.getProjectId()).isEqualTo(meetingEntity.getProjectId());
            assertThat(meeting.getTimestamp()).isEqualTo(meetingEntity.getTimestamp());
            assertThat(meeting.getSummary()).isEqualTo(meetingEntity.getSummary());
        }
    }

    @Test
    @DisplayName("Meeting 도메인 -> MeetingEntity 변환 테스트")
    void toEntityTest() {
        // given
        LocalDate today = LocalDate.now();
        User master = User.builder()
                .userId("user-456")
                .name("김철수")
                .build();
        
        UserEntity mockUserEntity = mock(UserEntity.class);

        Meeting meeting = Meeting.builder()
                .meetingId("meeting-456")
                .title("7월 20일의 회의록")
                .content("새로운 회의 내용입니다.")
                .master(master)
                .projectId("project-456")
                .timestamp(today)
                .summary("새로운 회의 요약입니다.")
                .build();

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {
            // when
            userMapperMock.when(() -> UserMapper.toEntity(master)).thenReturn(mockUserEntity);
            MeetingEntity meetingEntity = MeetingMapper.toEntity(meeting);

            // then
            assertThat(meetingEntity).isNotNull();
            assertThat(meetingEntity.getId()).isEqualTo(meeting.getMeetingId());
            assertThat(meetingEntity.getTitle()).isEqualTo(meeting.getTitle());
            assertThat(meetingEntity.getContent()).isEqualTo(meeting.getContent());
            assertThat(meetingEntity.getMaster()).isEqualTo(mockUserEntity);
            assertThat(meetingEntity.getProjectId()).isEqualTo(meeting.getProjectId());
            assertThat(meetingEntity.getTimestamp()).isEqualTo(meeting.getTimestamp());
            assertThat(meetingEntity.getSummary()).isEqualTo(meeting.getSummary());
        }
    }

    @Test
    @DisplayName("요약 정보가 없는 회의 변환 테스트")
    void meetingWithoutSummaryTest() {
        // given
        MeetingEntity entityWithoutSummary = MeetingEntity.builder()
                .id("meeting-no-summary")
                .title("요약 없는 회의")
                .content("회의 내용")
                .projectId("project-123")
                .build();

        Meeting domainWithoutSummary = Meeting.builder()
                .meetingId("meeting-domain-no-summary")
                .title("요약 없는 도메인 회의")
                .content("도메인 회의 내용")
                .projectId("project-123")
                .build();

        // when
        Meeting resultDomain = MeetingMapper.toDomain(entityWithoutSummary);
        MeetingEntity resultEntity = MeetingMapper.toEntity(domainWithoutSummary);

        // then
        assertThat(resultDomain.getSummary()).isNull();
        assertThat(resultEntity.getSummary()).isNull();
    }

    @Test
    @DisplayName("회의 업데이트 후 변환 테스트")
    void meetingUpdateTest() {
        // given
        User master = User.builder()
                .userId("user-123")
                .name("홍길동")
                .build();
        
        UserEntity mockUserEntity = mock(UserEntity.class);

        Meeting meeting = Meeting.builder()
                .meetingId("meeting-update")
                .title("원래 제목")
                .master(master)
                .build();
        
        // 회의 업데이트
        meeting.update("변경된 제목", master);
        meeting.addContent("추가된 내용");

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {
            // when
            userMapperMock.when(() -> UserMapper.toEntity(master)).thenReturn(mockUserEntity);
            MeetingEntity meetingEntity = MeetingMapper.toEntity(meeting);

            // then
            assertThat(meetingEntity.getTitle()).isEqualTo("변경된 제목");
            assertThat(meetingEntity.getContent()).isEqualTo("추가된 내용");
            assertThat(meetingEntity.getMaster()).isEqualTo(mockUserEntity);
        }
    }

    @Test
    @DisplayName("null 회의 변환 테스트")
    void nullMeetingTest() {
        // then
        assertThatThrownBy(() -> MeetingMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> MeetingMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}