package com.starmix.checkmate.application.port.out.persistence.dto;

import lombok.Builder;

@Builder
public record TaskCountPersistenceDto(
        Integer todoCount,
        Integer inProgressCount,
        Integer doneCount,
        Integer totalCount
) { }
