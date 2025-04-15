package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.MeetingEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.MeetingMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.MeetingMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.MeetingPersistencePort;
import com.starmix.checkmate.domain.meeting.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MeetingPersistenceAdapter implements MeetingPersistencePort {

    private final MeetingMongoRepository meetingMongoRepository;

    @Override
    public List<Meeting> finAllByProjectId(String projectId) {
        List<MeetingEntity> meetingEntities = meetingMongoRepository.findAllByProjectId(projectId);
        return meetingEntities.stream().map(MeetingMapper::toDomain).toList();
    }
}
