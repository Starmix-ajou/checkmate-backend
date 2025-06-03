package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskResponse;
import com.starmix.checkmate.adapter.in.sse.web.meeting.common.ActionItemDto;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.CreateActionItemsRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.FeedbackActionItemsRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.SaveMeetingRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.response.SaveMeetingResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateActionItemsFeignResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateMeetingFeignResponse;
import com.starmix.checkmate.adapter.out.redis.RedisType;
import com.starmix.checkmate.application.port.out.ai.AIPort;
import com.starmix.checkmate.application.port.out.persistence.*;
import com.starmix.checkmate.application.port.out.redis.RedisPort;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.meeting.Meeting;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetingPersistencePort meetingPersistencePort;
    private final JwtUtil jwtUtil;
    private final UserPersistencePort userPersistencePort;
    private final AIPort aIPort;
    private final RedisPort redisPort;
    private final EpicPersistencePort epicPersistencePort;
    private final TaskPersistencePort taskPersistencePort;
    private final SprintPersistencePort sprintPersistencePort;
    private final ProjectPersistencePort projectPersistencePort;
    private final NotificationService notificationService;

    public List<Meeting> getMeetingsByProjectId(String projectId) {
        return meetingPersistencePort.findAllByProjectId(projectId);
    }

    public Meeting createMeeting(String projectId) {
        User creator = jwtUtil.extractUser();
        Meeting meeting = Meeting.create(creator, projectId);
        String meetingId = meetingPersistencePort.save(meeting);
        Project project = projectPersistencePort.findById(meeting.getProjectId())
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));

        project.getMembers().forEach(
                member -> {
                    Notification notification = Notification.makeMeetingNotification(
                            member.getUserId(), meeting, project
                    );
                    notificationService.addNotifications(notification);
                }
        );
        
        return meetingPersistencePort.findById(meetingId)
                .orElseThrow(() -> new CustomException("Meeting not found", HttpStatus.NOT_FOUND));
    }

    public void deleteMeeting(String meetingId) {
        meetingPersistencePort.delete(meetingId);
    }

    public SaveMeetingResponse saveMeeting(SaveMeetingRequest request) {

        Meeting meeting = meetingPersistencePort.findById(request.masterId())
                .orElseThrow(() -> new CustomException("Meeting not found", HttpStatus.NOT_FOUND));
        User master = userPersistencePort.findById(request.masterId())
                        .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        meeting.save(request, master);
        meetingPersistencePort.save(meeting);
        CreateMeetingFeignResponse response = aIPort.saveMeeting(meeting);
        return SaveMeetingResponse.builder()
                .summary(response.summary())
                .actionItems(response.actionItems())
                .build();
    }

    public List<ActionItemDto> createActionItems(
            String meetingId,
            CreateActionItemsRequest request
    ) {
        Meeting meeting = meetingPersistencePort.findById(meetingId)
                .orElseThrow(() -> new CustomException("Meeting not found", HttpStatus.NOT_FOUND));
        List<CreateActionItemsFeignResponse> response = aIPort.createActionItems(
                meeting.getProjectId(), request.actionItems()
        );
        List<Task> tasks = response.stream().map(
                actionItem -> {
                    User assignee = userPersistencePort.findById(actionItem.assigneeId())
                            .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
                    Epic epic = epicPersistencePort.findById(actionItem.epicId())
                            .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));
                    return Task.init(
                            actionItem.title(), actionItem.description(), assignee,
                            actionItem.endDate(), epic
                    );
                }
        ).toList();
        redisPort.saveSet(RedisType.MEETING_ACTION_ITEMS, meetingId, tasks);
        return tasks.stream().map(ActionItemDto::fromDomain).toList();
    }

    public List<TaskResponse> feedbackActionItems(
            String meetingId,
            FeedbackActionItemsRequest request
    ) {
        List<Task> tasks = redisPort.getSet(
                RedisType.MEETING_ACTION_ITEMS,
                meetingId,
                Task.class
        );

        return request.tasks().stream().map(taskDto -> {
            Task cachedTask = tasks.stream().filter(
                    cachedMapTask -> cachedMapTask.getTitle().equals(taskDto.title())
            ).findFirst().orElse(null);
            taskPersistencePort.save(cachedTask);
            List<Sprint> sprints = sprintPersistencePort.findSprintByEpicId(Objects.requireNonNull(cachedTask).getEpic().getEpicId());
            Sprint sprint = cachedTask.getEpic().findCurrentSprint(sprints);
            return TaskResponse.fromDomain(cachedTask, sprint);
        }).toList();
    }
}