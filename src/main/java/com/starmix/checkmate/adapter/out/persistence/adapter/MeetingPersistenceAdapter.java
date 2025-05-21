package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.MeetingEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.MeetingMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.MeetingMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.MeetingPersistencePort;
import com.starmix.checkmate.domain.meeting.Meeting;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MeetingPersistenceAdapter implements MeetingPersistencePort {

    private final MeetingMongoRepository meetingMongoRepository;

    @Override
    public List<Meeting> findAllByProjectId(String projectId) {
        try {
            List<MeetingEntity> meetingEntities = meetingMongoRepository.findAllByProjectId(projectId);
            return meetingEntities.stream().map(MeetingMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String save(Meeting meeting) {
        try {
            MeetingEntity meetingEntity = MeetingMapper.toEntity(meeting);
            return meetingMongoRepository.save(meetingEntity).getId();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Meeting> findById(String id) {
        try {
            Optional<MeetingEntity> meetingEntity = meetingMongoRepository.findById(id);
            return meetingEntity.map(MeetingMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(String id) {
        try {
            meetingMongoRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
