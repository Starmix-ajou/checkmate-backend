package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @DBRef
    private List<User> members;
    @DBRef
    private User leader;
    private String imageUrl;
}