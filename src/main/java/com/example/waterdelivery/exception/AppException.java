package com.example.waterdelivery.exception;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class AppException extends RuntimeException {

    private final ZonedDateTime timestamp;

    public AppException(String message){
        super(message);
        this.timestamp = ZonedDateTime.now();
    }


}
