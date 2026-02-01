package com.example.waterdelivery.controller.dto;

import lombok.Data;

@Data
public class SignInResponse {
    private UserWithRolesDto user;
    private String token;
}
