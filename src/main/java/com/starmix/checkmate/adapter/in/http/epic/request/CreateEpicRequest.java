package com.starmix.checkmate.adapter.in.http.epic.request;

public record CreateEpicRequest(
        String title,
        String description,
        String sprintId
) { }