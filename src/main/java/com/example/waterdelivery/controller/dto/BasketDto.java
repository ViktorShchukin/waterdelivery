package com.example.waterdelivery.controller.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BasketDto {
    private UUID id;
    private UUID userId;
    private List<BasketItemDto> items;
}
