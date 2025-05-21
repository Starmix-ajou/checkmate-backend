package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.meeting.Meeting;

import java.util.List;
import java.util.Optional;

public interface MeetingPersistencePort {
    List<Meeting> findAllByProjectId(String projectId);
    String save(Meeting meeting);
    Optional<Meeting> findById(String id);
    void delete(String id);
}
