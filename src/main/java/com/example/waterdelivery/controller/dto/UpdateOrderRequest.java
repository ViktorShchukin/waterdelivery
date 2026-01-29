package com.example.waterdelivery.controller.dto;

import com.example.waterdelivery.model.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderRequest {
    private OrderStatus status;
}
