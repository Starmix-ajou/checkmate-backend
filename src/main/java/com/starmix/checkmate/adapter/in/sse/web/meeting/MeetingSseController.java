package com.starmix.checkmate.adapter.in.sse.web.meeting;

import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskResponse;
import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.common.SseType;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.CreateActionItemsRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.FeedbackActionItemsRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.SaveMeetingRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.response.SaveMeetingResponse;
import com.starmix.checkmate.application.service.MeetingService;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sse/meeting")
public class MeetingSseController {
    private final MeetingService meetingService;
    private final SseEmitterManager sseEmitterManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public void saveMeeting(@RequestBody SaveMeetingRequest request) {
        String userId = jwtUtil.extractUser().getUserId();
        SaveMeetingResponse response = meetingService.saveMeeting(request);
        sseEmitterManager.sendEventTo(SseType.PROJECT_SPRINT, userId, "save-meeting", response);
    }

    @PostMapping("/{meetingId}/action-items")
    public void createActionItems(
            @PathVariable String meetingId,
            @RequestBody CreateActionItemsRequest request
    ) {
        String userId = jwtUtil.extractUser().getUserId();
        List<TaskResponse> response = meetingService.createActionItems(meetingId,request);
        sseEmitterManager.sendEventTo(SseType.PROJECT_SPRINT, userId, "create-action-items", response);
    }

    @PutMapping("/{meetingId}/action-items")
    public void feedbackActionItems(
            @PathVariable String meetingId,
            @RequestBody FeedbackActionItemsRequest request
    ) {
        String userId = jwtUtil.extractUser().getUserId();
        List<TaskResponse> response = meetingService.feedbackActionItems(meetingId, request);
        sseEmitterManager.sendEventTo(SseType.PROJECT_SPRINT, userId, "feedback-action-items", response);
    }
}
