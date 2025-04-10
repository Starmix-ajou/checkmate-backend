package com.starmix.checkmate.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Meeting extends Base {
    private final String title;
    private final String content;
    private final List<String> participant;
    private final String masterId;
}