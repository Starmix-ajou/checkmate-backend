package com.starmix.checkmate.adapter.in.http.meeting;

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
    public ResponseEntity<List<Meeting>> getMeetingsByProjectId (
            @RequestParam String projectId
    ) {
        List<Meeting> meetings = meetingService.getMeetingsByProjectId(projectId);
        return ResponseEntity.ok().body(meetings);
    }

    @GetMapping("/create")
    public ResponseEntity<Meeting> createMeeting(@RequestParam String projectId) {
        Meeting meeting = meetingService.createMeeting(projectId);
        return ResponseEntity.ok().body(meeting);
    }
}