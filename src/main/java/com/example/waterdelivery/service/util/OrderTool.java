package com.example.waterdelivery.service.util;

import com.example.waterdelivery.config.AppMapperConfig;
import com.example.waterdelivery.model.Basket;
import com.example.waterdelivery.model.BasketItem;
import com.example.waterdelivery.model.Order;
import com.example.waterdelivery.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;

@Mapper(config = AppMapperConfig.class)
public interface OrderTool {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createdAt", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "orderStatus", expression = "java(com.example.waterdelivery.model.OrderStatus.CREATED)")
    Order createFromBasket(Basket basket, String deliveryAddress, ZonedDateTime deliveryDateTime);

    @Mapping(target = "order", ignore = true)
    OrderItem mapItem(BasketItem basketItem);
}
