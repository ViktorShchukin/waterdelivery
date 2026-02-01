package com.example.waterdelivery.controller.mapper;

import com.example.waterdelivery.config.AppMapperConfig;
import com.example.waterdelivery.controller.dto.UserDto;
import com.example.waterdelivery.controller.dto.UserWithRolesDto;
import com.example.waterdelivery.model.Role;
import com.example.waterdelivery.model.User;
import com.example.waterdelivery.model.UserRole;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(config = AppMapperConfig.class)
public interface UserMapper {

    UserDto toDto(User user);

    UserWithRolesDto toDtoWithRoles(User user);

    List<Role> mapRole(Set<UserRole> userRoles);

    default Role mapRole(UserRole userRole) {
        return userRole.getRole();
    }
}
