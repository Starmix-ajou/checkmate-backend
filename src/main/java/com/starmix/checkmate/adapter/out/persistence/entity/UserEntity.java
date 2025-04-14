package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.Role;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@SuperBuilder
@Document(collection = "users")
public class UserEntity extends BaseEntity {
    private String name;
    private String email;
    private List<Profile> profiles;
    private Role role;
}