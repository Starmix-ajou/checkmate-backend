package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.MeetingEntity;
import com.starmix.checkmate.domain.meeting.Meeting;


public class MeetingMapper {

    public static Meeting toDomain(MeetingEntity entity) {
        return Meeting.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .master(UserMapper.toDomain(entity.getMaster()))
                .projectId(entity.getProjectId())
                .meetingId(entity.getId())
                .timestamp(entity.getTimestamp())
                .summary(entity.getSummary())
                .build();
    }

    public static MeetingEntity toEntity(Meeting domain) {
        return MeetingEntity.builder()
                .id(domain.getMeetingId())
                .title(domain.getTitle())
                .content(domain.getContent())
                .master(UserMapper.toEntity(domain.getMaster()))
                .projectId(domain.getProjectId())
                .timestamp(domain.getTimestamp())
                .summary(domain.getSummary())
                .build();
    }
}
