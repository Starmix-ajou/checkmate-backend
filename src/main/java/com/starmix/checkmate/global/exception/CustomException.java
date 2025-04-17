package com.starmix.checkmate.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final String detailedMessage;

    public CustomException(String additionalDetail) {
        super(formatMessage(additionalDetail, null));
        this.detailedMessage = additionalDetail;
    }

    public CustomException(String additionalDetail, Throwable cause) {
        super(formatMessage(additionalDetail, cause), cause);
        this.detailedMessage = formatMessage(additionalDetail, cause);
    }

    private static String formatMessage(String additionalDetail, Throwable cause) {
        String baseMessage = additionalDetail != null ? ": " + additionalDetail : "";
        if (cause != null) {
            baseMessage += ". Cause: " + cause.getMessage();
        }
        return baseMessage;
    }
}



