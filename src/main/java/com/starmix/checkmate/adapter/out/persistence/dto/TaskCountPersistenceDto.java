package com.starmix.checkmate.adapter.out.persistence.dto;

import lombok.Builder;

@Builder
public record TaskCountPersistenceDto(
        Integer todoCount,
        Integer inProgressCount,
        Integer doneCount,
        Integer totalCount
) { }
