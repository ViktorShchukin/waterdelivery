package com.example.waterdelivery.controller.mapper;

import com.example.waterdelivery.config.AppMapperConfig;
import com.example.waterdelivery.controller.dto.AuthResultDto;
import com.example.waterdelivery.model.AuthResult;
import com.example.waterdelivery.model.Role;
import com.example.waterdelivery.model.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(config = AppMapperConfig.class)
public interface AuthMapper {

    @Mapping(target = ".", source = "user")
    AuthResultDto toDto(AuthResult authResult);

    List<Role> mapRole(Set<UserRole> userRoles);

    default Role mapRole(UserRole userRole) {
        return userRole.getRole();
    }
}
