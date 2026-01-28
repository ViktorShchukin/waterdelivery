package com.example.waterdelivery.controller.mapper;

import com.example.waterdelivery.config.AppMapperConfig;
import com.example.waterdelivery.controller.dto.BasketDto;
import com.example.waterdelivery.model.Basket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = AppMapperConfig.class)
public interface BasketMapper {

    @Mapping(target = "userId", source = "basket.user.id")
    BasketDto toDto(Basket basket);
}
