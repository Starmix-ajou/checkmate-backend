package com.starmix.checkmate.adapter.in.rest.web.epic.request;

public record CreateEpicRequest(
        String title,
        String description
) { }