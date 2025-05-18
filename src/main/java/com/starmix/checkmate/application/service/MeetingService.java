package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.http.meeting.request.UpdateMeetingRequest;
import com.starmix.checkmate.application.port.out.persistence.MeetingPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.domain.meeting.Meeting;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetingPersistencePort meetingPersistencePort;
    private final JwtUtil jwtUtil;
    private final UserPersistencePort userPersistencePort;

    public List<Meeting> getMeetingsByProjectId(String projectId) {
        return meetingPersistencePort.findAllByProjectId(projectId);
    }

    public Meeting createMeeting(String projectId) {
        User creator = jwtUtil.extractUser();
        System.out.println(projectId);
        Meeting meeting = Meeting.create(creator, projectId);
        String meetingId = meetingPersistencePort.save(meeting);
        return meetingPersistencePort.findById(meetingId)
                .orElseThrow(() -> new CustomException("Meeting not found", HttpStatus.NOT_FOUND));
    }

    public Meeting updateMeeting(String meetingId, UpdateMeetingRequest request) {
        Meeting meeting = meetingPersistencePort.findById(meetingId)
                .orElseThrow(() -> new CustomException("Meeting not found", HttpStatus.NOT_FOUND));
        User master = userPersistencePort.findById(request.masterId())
                        .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        List<User> participants = request.participantIds().stream().map(
                participantId -> userPersistencePort.findById(participantId)
                        .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND))
        ).toList();

        meeting.update(request.title(), request.content(), master, participants);
        meetingPersistencePort.save(meeting);
        return meeting;
    }
}