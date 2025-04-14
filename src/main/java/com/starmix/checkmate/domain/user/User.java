package com.starmix.checkmate.domain.user;

import com.starmix.checkmate.domain.Base;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class User extends Base {
    private final String name;
    private final String email;
    private final List<Profile> profiles;
    private final Role role;
}