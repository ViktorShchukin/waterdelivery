package com.example.waterdelivery.controller.mapper;

import com.example.waterdelivery.config.AppMapperConfig;
import com.example.waterdelivery.controller.dto.UserDto;
import com.example.waterdelivery.model.User;
import org.mapstruct.Mapper;

@Mapper(config = AppMapperConfig.class)
public interface UserMapper {

    UserDto toDto(User user);
}
