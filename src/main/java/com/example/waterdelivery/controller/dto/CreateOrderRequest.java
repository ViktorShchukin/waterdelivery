package com.example.waterdelivery.controller.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateOrderRequest {
    private String deliveryAddress;
    private ZonedDateTime deliveryDateTime;
}
