package com.starmix.checkmate.adapter.in.http.project.request;

import java.util.List;

public record UpdateMemberRequest(
        List<String> positions
) { }
