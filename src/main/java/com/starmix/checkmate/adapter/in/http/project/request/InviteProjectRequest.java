package com.starmix.checkmate.adapter.in.http.project.request;

import com.starmix.checkmate.adapter.in.common.ProfileDto;
import com.starmix.checkmate.domain.user.Role;

public record InviteProjectRequest(
        String email,
        ProfileDto profile,
        Role role
) { }
