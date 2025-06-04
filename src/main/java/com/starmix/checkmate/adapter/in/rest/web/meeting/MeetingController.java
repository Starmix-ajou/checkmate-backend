package com.starmix.checkmate.adapter.in.rest.web.meeting;

import com.starmix.checkmate.adapter.in.rest.web.meeting.response.MeetingResponse;
import com.starmix.checkmate.application.service.MeetingService;
import com.starmix.checkmate.domain.meeting.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping
    public ResponseEntity<List<MeetingResponse>> getMeetingsByProjectId (
            @RequestParam String projectId
    ) {
        List<Meeting> meetings = meetingService.getMeetingsByProjectId(projectId);
        List<MeetingResponse> response = meetings.stream().map(MeetingResponse::from).toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingResponse> getMeeting (
            @RequestParam String meetingId
    ) {
        Meeting meetings = meetingService.getMeeting(meetingId);
        MeetingResponse response = MeetingResponse.from(meetings);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/create")
    public ResponseEntity<MeetingResponse> createMeeting(@RequestParam String projectId) {
        Meeting meeting = meetingService.createMeeting(projectId);
        MeetingResponse response = MeetingResponse.from(meeting);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable String meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.ok().build();
    }
}