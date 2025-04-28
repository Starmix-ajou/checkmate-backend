package com.starmix.checkmate.global.exception;

import com.starmix.checkmate.adapter.out.slack.log.SlackLabel;
import com.starmix.checkmate.adapter.out.slack.log.SlackLogLevel;
import com.starmix.checkmate.application.port.out.slack.SlackPort;
import com.starmix.checkmate.infrastructure.config.SpringEnv;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final SlackPort slackPort;
    private final SpringEnv springEnv;

    private String formatAsJson(String description) {
        if (description == null) {
            return "";
        }
        return description
                .replace("(", "(\n  ")
                .replace(")", "\n)")
                .replace(", ", ",\n  ");
    }

    private void logToSlack(LocalDateTime timestamp, String message, SlackLogLevel logLevel) {
        if (springEnv.isDevProfile() || springEnv.isProdProfile()) {
            slackPort.sendMsg(timestamp, "Exception 발생", formatAsJson(message), logLevel, SlackLabel.SYSTEM_ALERT);
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException ex) {
        ex.printStackTrace();

        LocalDateTime timestamp = LocalDateTime.now();

        StringBuilder details = new StringBuilder(ex.getMessage());
        if (ex.getCause() != null) {
            details.append("\nCaused by: ").append(ex.getCause().getMessage());
        }

        logToSlack(timestamp, details.toString(), SlackLogLevel.ERROR);

        ErrorDto errorDto = new ErrorDto(details.toString());
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(errorDto);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDto> handleNullPointerException(NullPointerException ex) {
        ex.printStackTrace();

        LocalDateTime timestamp = LocalDateTime.now();
        logToSlack(timestamp, ex.getMessage(), SlackLogLevel.WARNING);

        ErrorDto errorDto = new ErrorDto("잘못된 요청입니다: Null 값이 존재합니다.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();

        LocalDateTime timestamp = LocalDateTime.now();
        logToSlack(timestamp, ex.getMessage(), SlackLogLevel.WARNING);

        StringBuilder errorMessageBuilder = new StringBuilder("입력값 검증 오류: ");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessageBuilder.append("[")
                    .append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("] ");
        }
        ErrorDto errorDto = new ErrorDto(errorMessageBuilder.toString());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneralException(Exception ex) {
        ex.printStackTrace();

        LocalDateTime timestamp = LocalDateTime.now();
        logToSlack(timestamp, ex.getMessage(), SlackLogLevel.ERROR);

        ErrorDto errorDto = new ErrorDto("서버 내부 오류가 발생했습니다.");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }
}