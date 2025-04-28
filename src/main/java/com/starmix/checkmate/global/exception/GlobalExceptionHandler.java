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

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException ex) {
        ex.printStackTrace();

        LocalDateTime timestamp = LocalDateTime.now();
        String formattedDetails = formatAsJson(ex.getDetailedMessage());

        if(springEnv.isDevProfile() || springEnv.isProdProfile()) {
            slackPort.sendMsg(timestamp, "Exception 발생", formattedDetails, SlackLogLevel.ERROR, SlackLabel.SYSTEM_ALERT);
        }

        ErrorDto errorDto = new ErrorDto(ex.getDetailedMessage());
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(errorDto);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDto> handleNullPointerException(NullPointerException ex) {
        ex.printStackTrace();

        LocalDateTime timestamp = LocalDateTime.now();
        String formattedDetails = formatAsJson(ex.getMessage());

        if(springEnv.isDevProfile() || springEnv.isProdProfile()) {
            slackPort.sendMsg(timestamp, "Exception 발생", formattedDetails, SlackLogLevel.WARNING, SlackLabel.SYSTEM_ALERT);
        }

        ErrorDto errorDto = new ErrorDto("잘못된 요청입니다: Null 값이 존재합니다.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();

        LocalDateTime timestamp = LocalDateTime.now();
        String formattedDetails = formatAsJson(ex.getMessage());

        if(springEnv.isDevProfile() || springEnv.isProdProfile()) {
            slackPort.sendMsg(timestamp, "Exception 발생", formattedDetails, SlackLogLevel.WARNING, SlackLabel.SYSTEM_ALERT);
        }

        StringBuilder errorMessageBuilder = new StringBuilder("입력값 검증 오류: ");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessageBuilder.append("[")
                    .append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("] ");
        }
        String detailedMessage = errorMessageBuilder.toString();
        ErrorDto errorDto = new ErrorDto(detailedMessage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneralException(Exception ex) {
        ex.printStackTrace();

        LocalDateTime timestamp = LocalDateTime.now();
        String formattedDetails = formatAsJson(ex.getMessage());

        if(springEnv.isDevProfile() || springEnv.isProdProfile()) {
            slackPort.sendMsg(timestamp, "Exception 발생", formattedDetails, SlackLogLevel.ERROR, SlackLabel.SYSTEM_ALERT);
        }

        ErrorDto errorDto = new ErrorDto("서버 내부 오류가 발생했습니다.");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }
}
