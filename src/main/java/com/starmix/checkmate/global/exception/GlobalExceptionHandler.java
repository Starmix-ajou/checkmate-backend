package com.starmix.checkmate.global.exception;

import com.starmix.checkmate.adapter.out.slack.log.SlackLabel;
import com.starmix.checkmate.adapter.out.slack.log.SlackLogLevel;
import com.starmix.checkmate.application.port.out.slack.SlackPort;
import com.starmix.checkmate.infrastructure.config.SpringEnv;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final SlackPort slackPort;
    private final SpringEnv springEnv;

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("비정상적인 접근입니다."));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDto> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErrorDto("비정상적인 접근입니다."));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException ex) {
        ex.printStackTrace();
        LocalDateTime timestamp = LocalDateTime.now();
        logToSlack(timestamp, ex, SlackLogLevel.ERROR);

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ErrorDto("요청을 처리하는 중 오류가 발생했습니다."));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDto> handleNullPointerException(NullPointerException ex) {
        ex.printStackTrace();
        LocalDateTime timestamp = LocalDateTime.now();
        logToSlack(timestamp, ex, SlackLogLevel.WARNING);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto("잘못된 요청입니다."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        LocalDateTime timestamp = LocalDateTime.now();
        logToSlack(timestamp, ex, SlackLogLevel.WARNING);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto("입력값이 유효하지 않습니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        LocalDateTime timestamp = LocalDateTime.now();
        logToSlack(timestamp, ex, SlackLogLevel.ERROR);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("서버 내부 오류가 발생했습니다."));
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

    private String getStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private void logToSlack(LocalDateTime timestamp, Throwable ex, SlackLogLevel logLevel) {
        if (springEnv.isDevProfile() || springEnv.isProdProfile()) {
            slackPort.sendMsg(timestamp, "Exception 발생", formatAsJson(getStackTrace(ex)), logLevel, SlackLabel.SYSTEM_ALERT);
        }
    }
}