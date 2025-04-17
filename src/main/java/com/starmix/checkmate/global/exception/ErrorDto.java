package com.starmix.checkmate.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    private int detailStatusCode;
    private String message;
}
