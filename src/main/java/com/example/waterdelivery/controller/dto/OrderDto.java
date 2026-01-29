package com.example.waterdelivery.controller.dto;

import com.example.waterdelivery.model.OrderStatus;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private UserDto user;
    private String deliveryAddress;
    private ZonedDateTime deliveryDateTime;
    private OrderStatus orderStatus;
    private List<OrderItemDto> items;
    private ZonedDateTime createdAt;

}
