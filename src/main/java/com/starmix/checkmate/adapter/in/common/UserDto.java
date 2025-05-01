package com.starmix.checkmate.adapter.in.common;

import com.starmix.checkmate.domain.user.Profile;

public record UserDto(
        String email,
        Profile profile
) { }
