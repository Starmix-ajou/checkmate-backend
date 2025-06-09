package com.starmix.checkmate.domain;

import com.starmix.checkmate.adapter.in.sse.web.meeting.request.SaveMeetingRequest;
import com.starmix.checkmate.domain.meeting.Meeting;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingTest {
    private Meeting meeting;
    private User master;
    private String projectId;

    @BeforeEach
    void setUp() {
        projectId = "project-id";
        master = User.builder()
                .userId("user-id")
                .name("회의 마스터")
                .email("master@test.com")
                .build();

        meeting = Meeting.builder()
                .meetingId("meeting-id")
                .title("테스트 회의")
                .content("회의 내용")
                .master(master)
                .projectId(projectId)
                .timestamp(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("회의록 생성 테스트")
    void createMeeting() {
        // when
        Meeting newMeeting = Meeting.create(master, projectId);
        String expectedTitle = LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일")) + "의 회의록";

        // then
        assertThat(newMeeting.getMeetingId()).isNotNull();
        assertThat(newMeeting.getTitle()).isEqualTo(expectedTitle);
        assertThat(newMeeting.getMaster()).isEqualTo(master);
        assertThat(newMeeting.getProjectId()).isEqualTo(projectId);
        assertThat(newMeeting.getTimestamp()).isEqualTo(LocalDate.now());
        assertThat(newMeeting.getContent()).isNull();
        assertThat(newMeeting.getSummary()).isNull();
    }

    @Test
    @DisplayName("회의록 저장 테스트")
    void saveMeeting() {
        // given
        String newMeetingId = "new-meeting-id";
        String newTitle = "새로운 회의";
        String newContent = "새로운 회의 내용";
        String summary = "회의 요약";

        SaveMeetingRequest request = new SaveMeetingRequest(
                newMeetingId,
                newTitle,
                newContent,
                master.getUserId()
        );

        // when
        meeting.save(request, master, summary);

        // then
        assertThat(meeting.getMeetingId()).isEqualTo(newMeetingId);
        assertThat(meeting.getTitle()).isEqualTo(newTitle);
        assertThat(meeting.getContent()).isEqualTo(newContent);
        assertThat(meeting.getMaster()).isEqualTo(master);
        assertThat(meeting.getSummary()).isEqualTo(summary);
    }

    @Test
    @DisplayName("회의록 업데이트 테스트")
    void updateMeeting() {
        // given
        String newTitle = "수정된 회의 제목";
        User newMaster = User.builder()
                .userId("new-master")
                .name("새로운 마스터")
                .build();

        // when
        meeting.update(newTitle, newMaster);

        // then
        assertThat(meeting.getTitle()).isEqualTo(newTitle);
        assertThat(meeting.getMaster()).isEqualTo(newMaster);
    }

    @Test
    @DisplayName("회의 내용 추가 테스트")
    void addContent() {
        // given
        String newContent = "새로운 회의 내용";

        // when
        meeting.addContent(newContent);

        // then
        assertThat(meeting.getContent()).isEqualTo(newContent);
    }
}