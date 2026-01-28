package com.example.waterdelivery.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BasketItemDto {
    private UUID id;
    private ProductDto product;
    private Long quantity;
}
