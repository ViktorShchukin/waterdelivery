package com.example.waterdelivery.controller;

import com.example.waterdelivery.controller.dto.UserWithRolesDto;
import com.example.waterdelivery.controller.mapper.UserMapper;
import com.example.waterdelivery.repository.UserRepository;
import com.example.waterdelivery.security.CustomUserDetails;
import com.example.waterdelivery.security.annotation.IsAdmin;
import com.example.waterdelivery.security.annotation.IsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @IsUser
    @GetMapping("/me")
    public ResponseEntity<UserWithRolesDto> getMe(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(userMapper.toDtoWithRoles(userDetails.getUser()));
    }

    @IsAdmin
    @GetMapping()
    public ResponseEntity<Page<UserWithRolesDto>> getAll(
            @PageableDefault Pageable pageable
            ){
        var res = userRepository.findAll(pageable)
                .map(userMapper::toDtoWithRoles);
        return ResponseEntity.ok(res);
    }

}
