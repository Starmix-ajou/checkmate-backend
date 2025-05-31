package com.starmix.checkmate.adapter.in.rest.web.project.request;

import java.util.List;

public record UpdateMemberRequest(
        List<String> positions
) { }
