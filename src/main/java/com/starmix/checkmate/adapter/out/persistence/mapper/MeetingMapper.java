package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.MeetingEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.meeting.Meeting;
import com.starmix.checkmate.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingMapper {

    public static Meeting toDomain(MeetingEntity entity) {
        List<User> participants = entity.getParticipants().stream()
                .map(UserMapper::toDomain).toList();

        return Meeting.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .participants(participants)
                .master(UserMapper.toDomain(entity.getMaster()))
                .projectId(entity.getProjectId())
                .meetingId(entity.getId())
                .build();
    }

    public static MeetingEntity toEntity(Meeting domain) {
        List<UserEntity> participants = domain.getParticipants().stream()
                .map(UserMapper::toEntity).toList();

        return MeetingEntity.builder()
                .title(domain.getTitle())
                .content(domain.getContent())
                .participants(participants)
                .master(UserMapper.toEntity(domain.getMaster()))
                .projectId(domain.getProjectId())
                .build();
    }
}
