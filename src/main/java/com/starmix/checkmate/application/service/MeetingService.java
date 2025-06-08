package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.rest.web.meeting.request.UpdateMeetingRequest;
import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskResponse;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.CreateActionItemsRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.FeedbackActionItemsRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.SaveMeetingRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.response.SaveMeetingResponse;
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

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class MeetingService {
    private final MeetingPersistencePort meetingPersistencePort;
    private final JwtUtil jwtUtil;
    private final UserPersistencePort userPersistencePort;
    private final AIPort aIPort;
    private final RedisPort redisPort;
    private final EpicPersistencePort epicPersistencePort;
    private final SprintPersistencePort sprintPersistencePort;
    private final ProjectPersistencePort projectPersistencePort;
    private final NotificationService notificationService;
    private final TaskPersistencePort taskPersistencePort;

    public List<Meeting> getMeetingsByProjectId(String projectId) {
        return meetingPersistencePort.findAllByProjectId(projectId);
    }

    public Meeting getMeeting(String meetingId) {
        return meetingPersistencePort.findById(meetingId)
                .orElseThrow(() -> new CustomException("Meeting not found", HttpStatus.NOT_FOUND));
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
        Meeting meeting = meetingPersistencePort.findById(request.meetingId())
                .orElseThrow(() -> new CustomException("Meeting not found", HttpStatus.NOT_FOUND));
        User master = userPersistencePort.findById(request.masterId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        meeting.addContent(request.content());

        CreateMeetingFeignResponse response = aIPort.saveMeeting(meeting);

        List<Task> tasks = response.actionItems().stream().map(
                actionItem -> {
                    User assignee = null;
                    if(actionItem.assigneeId() != null) {
                        assignee = userPersistencePort.findById(actionItem.assigneeId())
                                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
                    }
                    Epic epic = null;
                    if(actionItem.epicId() != null) {
                        epic = epicPersistencePort.findById(actionItem.epicId())
                                .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));

                    }
                    LocalDate endDate = actionItem.endDate();
                    if(endDate == null) {
                        Sprint sprint = sprintPersistencePort.findCurrentSprint(meeting.getProjectId())
                                .orElseThrow(() -> new CustomException("Sprint not found", HttpStatus.NOT_FOUND));
                        endDate = sprint.getEndDate();
                    }

                    return Task.init(
                            actionItem.title(), actionItem.description(),
                            assignee, endDate, epic
                    );
                }
        ).toList();

        meeting.save(request, master, response.summary());
        meetingPersistencePort.save(meeting);

        redisPort.saveSet(RedisType.MEETING_ACTION_ITEMS, meeting.getMeetingId(), tasks, 10, TimeUnit.MINUTES);

        return SaveMeetingResponse.builder()
                .summary(response.summary())
                .actionItems(
                        response.actionItems().stream()
                                .map(CreateMeetingFeignResponse.ActionItem::title).toList()
                )
                .build();
    }

    public List<TaskResponse> createActionItems(
            String meetingId, CreateActionItemsRequest request
    ) {
        List<Task> cachedTasks = redisPort.getSet(
                RedisType.MEETING_ACTION_ITEMS,
                meetingId,
                Task.class
        );
        redisPort.delete(RedisType.MEETING_ACTION_ITEMS, meetingId);

        List<Task> tasks = cachedTasks.stream()
                .filter(cachedTask -> request.actionItems().contains(cachedTask.getTitle()))
                .toList();

        return tasks.stream().map(task -> {
            Sprint sprint = null;
            if(task.getEpic() != null) {
                List<Sprint> sprints = sprintPersistencePort.findSprintByEpicId(task.getEpic().getEpicId());
                sprint = task.getEpic().findCurrentSprint(sprints);
            }
            return TaskResponse.fromDomain(task, sprint);
        }).toList();
    }

    public List<TaskResponse> feedbackActionItems(
            String meetingId, FeedbackActionItemsRequest request
    ) {
        Meeting meeting = meetingPersistencePort.findById(meetingId)
                .orElseThrow(() -> new CustomException("Meeting not found", HttpStatus.NOT_FOUND));

        Sprint sprint = sprintPersistencePort.findCurrentSprint(meeting.getProjectId())
                .orElseThrow(() -> new CustomException("Sprint not found", HttpStatus.NOT_FOUND));

        List<Task> tasks = request.tasks().stream().map(
                actionItem -> {
                    User assignee = userPersistencePort.findByEmail(actionItem.assigneeEmail())
                            .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
                    Epic epic = epicPersistencePort.findById(actionItem.epicId())
                            .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));

                    Task task = Task.create(
                            actionItem.title(), actionItem.description(), actionItem.status(),
                            assignee, actionItem.startDate(), actionItem.endDate(),
                            actionItem.priority(), epic
                    );
                    taskPersistencePort.save(task);
                    return task;
                }
        ).toList();
        return tasks.stream().map(task -> TaskResponse.fromDomain(task, sprint)).toList();
    }

    public void updateMeeting(String meetingId, UpdateMeetingRequest request) {
        Meeting meeting = meetingPersistencePort.findById(meetingId)
                .orElseThrow(() -> new CustomException("Meeting not found", HttpStatus.NOT_FOUND));
        User master = userPersistencePort.findById(request.masterId())
                .orElseThrow(() -> new CustomException("Master not found", HttpStatus.NOT_FOUND));

        meeting.update(request.title(), master);
        meetingPersistencePort.save(meeting);
    }
}