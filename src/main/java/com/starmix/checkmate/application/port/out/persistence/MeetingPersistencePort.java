package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.meeting.Meeting;

import java.util.List;

public interface MeetingPersistencePort {
    List<Meeting> findAllByProjectId(String projectId);
}
