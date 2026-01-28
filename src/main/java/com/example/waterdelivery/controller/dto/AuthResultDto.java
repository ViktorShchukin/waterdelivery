package com.example.waterdelivery.controller.dto;

import com.example.waterdelivery.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class AuthResultDto {
    private String login;
    private String token;
    private List<Role> roles;
}
