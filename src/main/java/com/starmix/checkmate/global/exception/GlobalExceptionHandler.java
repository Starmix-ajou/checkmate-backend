package com.starmix.checkmate.global.exception;
import com.starmix.checkmate.infrastructure.config.SpringEnv;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final SlackPort slackPort;
    private final SpringEnv springEnv;

    private void logException(Exception ex, String title) {
        LocalDateTime timestamp = LocalDateTime.now();
        String traceId = org.slf4j.MDC.get("trace_id");
        String formattedDetails = formatAsJson(ex.getMessage());

        logger.error("[{}][{}] {}: {}",
                timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                traceId,
                title,
                ex.getMessage(),
                ex);

        if(springEnv.isDevProfile() || springEnv.isProdProfile()) {
            slackPort.sendMsgByTraceId(timestamp, title, formattedDetails, SlackLogLevel.ERROR, SlackLabel.SYSTEM_ALERT, traceId);
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException ex) {
        logException(ex, ex.getMessage());

        LocalDateTime timestamp = LocalDateTime.now();
        logger.error("[{}] {}", timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), errorInfo.getMessage(), ex);

        if(springEnv.isDevProfile() || springEnv.isProdProfile()) {
            String detailedMessage = ex.getDetailedMessage();
            String formattedDetails = formatAsJson(detailedMessage);
            slackPort.sendMsgByTraceId(timestamp, errorInfo.getMessage(), formattedDetails, SlackLogLevel.ERROR, SlackLabel.SYSTEM_ALERT);
        }

        ErrorDto errorDto = new ErrorDto(errorInfo.getDetailStatusCode(), ex.getDetailedMessage());
        return ResponseEntity
                .status(errorInfo.getStatus())
                .body(errorDto);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDto> handleNullPointerException(NullPointerException ex) {
        logException(ex, "NullPointerException");

        LocalDateTime timestamp = LocalDateTime.now();
        logger.error("[{}] NullPointerException occurred: {}", timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ex.getMessage(), ex);

        if(springEnv.isDevProfile() || springEnv.isProdProfile()) {
            String formattedDetails = formatAsJson(ex.getMessage());
            slackPort.sendMsgByTraceId(timestamp,"NullPointerException", formattedDetails, SlackLogLevel.ERROR, SlackLabel.SYSTEM_ALERT);
        }
        ErrorDto errorDto = new ErrorDto(400, "잘못된 요청입니다: Null 값이 존재합니다.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        logException(ex, "Validation Error");

        StringBuilder errorMessageBuilder = new StringBuilder("입력값 검증 오류: ");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessageBuilder.append("[")
                    .append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("] ");
        }
        String detailedMessage = errorMessageBuilder.toString();
        ErrorDto errorDto = new ErrorDto(400, detailedMessage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    private String formatAsJson(String description) {
        if (description == null) {
            return "";
        }
        return description
                .replace("(", "(\n  ")
                .replace(")", "\n)")
                .replace(", ", ",\n  ");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneralException(Exception ex) {
        logException(ex, "500 Internal Server Error");

        ErrorDto errorDto = new ErrorDto(500, "서버 내부 오류가 발생했습니다.");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }
}
