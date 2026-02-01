package com.example.waterdelivery.controller.dto;

import com.example.waterdelivery.model.Role;
import com.example.waterdelivery.model.UserRole;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserWithRolesDto {
    private UUID id;
    private String login;
    private Set<Role> roles;
}
