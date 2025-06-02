package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.user.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@SuperBuilder
@Document(collection = "users")
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    private String name;
    private String email;
    private String profileImageUrl;
    private List<Profile> profiles;
}