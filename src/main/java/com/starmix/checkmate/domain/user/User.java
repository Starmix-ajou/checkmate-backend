package com.starmix.checkmate.domain.user;

import com.starmix.checkmate.domain.Base;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class User extends Base {
    private final String name;
    private final String email;
    private final List<Profile> profiles;
    private final Role role;
}