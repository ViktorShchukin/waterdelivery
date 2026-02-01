package com.example.waterdelivery.exception.handler.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class AppErrorResponse {
    private ZonedDateTime timestamp;
    private String message;
    private Integer status;

}
