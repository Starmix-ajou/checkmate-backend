package com.starmix.checkmate.adapter.in.http.dailyScrum.request;

import java.util.List;

public record UpdateDailyScrumRequest(
        List<String> todoTaskIds,
        List<String> doneTaskIds
) { }