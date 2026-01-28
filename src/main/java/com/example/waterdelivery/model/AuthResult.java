package com.example.waterdelivery.model;

import lombok.*;

@Data
@AllArgsConstructor
public class AuthResult {
    private User user;
    private String token;
}
