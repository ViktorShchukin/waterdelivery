package com.example.waterdelivery.exception.handler;

import com.example.waterdelivery.exception.AppException;
import com.example.waterdelivery.exception.ResourceNotFoundException;
import com.example.waterdelivery.exception.UserAlredyExist;
import com.example.waterdelivery.exception.handler.dto.AppErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlredyExist.class)
    public ResponseEntity<AppErrorResponse> handleUserExist(UserAlredyExist e) {
        var status = HttpStatus.CONFLICT.value();
        var res = mapAppExc(e, status);
        return ResponseEntity.status(status).body(res);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AppErrorResponse> handleNotFound(ResourceNotFoundException e) {
        var status = HttpStatus.NOT_FOUND.value();
        var res = mapAppExc(e, status);
        return ResponseEntity.status(status).body(res);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppErrorResponse> handleAppException(AppException e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        var res = AppErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(e.getTimestamp())
                .status(status)
                .build();
        return ResponseEntity.status(status).body(res);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AppErrorResponse> handleRuntimeException(RuntimeException e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        var res = AppErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(ZonedDateTime.now())
                .status(status)
                .build();
        return ResponseEntity.status(status).body(res);
    }

    private AppErrorResponse mapAppExc(AppException e, Integer status) {
        return AppErrorResponse.builder()
                .message(e.getMessage())
                .status(status)
                .timestamp(e.getTimestamp())
                .build();
    }
}
