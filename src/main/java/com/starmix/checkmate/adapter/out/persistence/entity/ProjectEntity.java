package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.common.Stack;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "meetings")
@Getter
@SuperBuilder
public class ProjectEntity extends BaseEntity {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Stack> stacks;
    @DBRef
    private List<UserEntity> members;
    @DBRef
    private UserEntity leader;
}