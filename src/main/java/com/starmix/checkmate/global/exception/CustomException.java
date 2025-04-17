package com.starmix.checkmate.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final String detailedMessage;
    private final HttpStatus httpStatus;

    public CustomException(String detailedMessage, HttpStatus httpStatus) {
        this.detailedMessage = detailedMessage;
        this.httpStatus = httpStatus;
    }
}



