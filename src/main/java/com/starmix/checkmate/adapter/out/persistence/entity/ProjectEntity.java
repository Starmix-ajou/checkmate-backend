package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.common.Stack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "projects")
@Getter
@SuperBuilder
@NoArgsConstructor
public class ProjectEntity extends BaseEntity {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Stack> stacks;
    private List<String> memberIds;
    private String leaderId;
    private String imageUrl;
}