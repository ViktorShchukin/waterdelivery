package com.example.waterdelivery.controller.mapper;

import com.example.waterdelivery.config.AppMapperConfig;
import com.example.waterdelivery.controller.dto.OrderDto;
import com.example.waterdelivery.model.Order;
import org.mapstruct.Mapper;

@Mapper(config = AppMapperConfig.class)
public interface OrderMapper {

    OrderDto toDto(Order order);
}
