package com.example.waterdelivery.controller;

import com.example.waterdelivery.controller.dto.AuthResultDto;
import com.example.waterdelivery.controller.dto.LoginRequest;
import com.example.waterdelivery.controller.mapper.AuthMapper;
import com.example.waterdelivery.model.AuthResult;
import com.example.waterdelivery.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthMapper authMapper;

    public AuthController(AuthService authService, AuthMapper authMapper) {
        this.authService = authService;
        this.authMapper = authMapper;
    }

    @PostMapping("/login")
    ResponseEntity<AuthResultDto> login(@RequestBody LoginRequest login) {
        AuthResult res = authService.login(login.getLogin(), login.getPassword());
        return ResponseEntity.ok(authMapper.toDto(res));
    }

    @GetMapping("/hello")
    ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello, World!");
    }
}
