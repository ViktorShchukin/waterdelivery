package com.example.waterdelivery.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class OrderStatusMessage {
    UUID orderId;
    OrderStatus status;
    ZonedDateTime changeAt;
}
