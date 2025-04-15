package com.starmix.checkmate.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "meetings")
@Getter
@SuperBuilder
public class MeetingEntity extends BaseEntity {
    private String title;
    private String content;
    @DBRef
    private List<UserEntity> participants;
    @DBRef
    private UserEntity master;
    private String projectId;
}