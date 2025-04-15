package com.starmix.checkmate.application.service;

import com.starmix.checkmate.application.port.out.persistence.MeetingPersistencePort;
import com.starmix.checkmate.domain.meeting.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetingPersistencePort meetingPersistencePort;

    public List<Meeting> getMeetingsByProjectId(String projectId) {
        return meetingPersistencePort.findAllByProjectId(projectId);
    }
}