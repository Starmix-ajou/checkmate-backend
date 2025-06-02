package com.starmix.checkmate.adapter.in.sse.web.meeting;

import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskResponse;
import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.web.meeting.common.ActionItemDto;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.CreateActionItemsRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.FeedbackActionItemsRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.request.SaveMeetingRequest;
import com.starmix.checkmate.adapter.in.sse.web.meeting.response.SaveMeetingResponse;
import com.starmix.checkmate.application.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sse/meeting")
public class MeetingSseController {
    private final MeetingService meetingService;
    private final SseEmitterManager sseEmitterManager;

    @PostMapping
    public void saveMeeting(@RequestBody SaveMeetingRequest request) {
        SaveMeetingResponse response = meetingService.saveMeeting(request);
        sseEmitterManager.sendEvent("save-meeting", response);
    }

    @PostMapping("/{meetingId}/action-items")
    public void createActionItems(
            @PathVariable String meetingId,
            @RequestBody CreateActionItemsRequest request
    ) {
        List<ActionItemDto> response = meetingService.createActionItems(meetingId,request);
        sseEmitterManager.sendEvent("create-action-items", response);
    }

    @PutMapping("/{meetingId}/action-items")
    public void feedbackActionItems(
            @PathVariable String meetingId,
            @RequestBody FeedbackActionItemsRequest request
    ) {
        List<TaskResponse> response = meetingService.feedbackActionItems(meetingId, request);
        sseEmitterManager.sendEvent("create-action-items", response);
    }
}
