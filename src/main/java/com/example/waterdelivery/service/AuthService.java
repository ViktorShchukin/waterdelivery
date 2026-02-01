package com.example.waterdelivery.service;

import com.example.waterdelivery.exception.ResourceNotFoundException;
import com.example.waterdelivery.exception.UserAlredyExist;
import com.example.waterdelivery.model.AuthResult;
import com.example.waterdelivery.model.Role;
import com.example.waterdelivery.model.User;
import com.example.waterdelivery.model.UserRole;
import com.example.waterdelivery.repository.UserRepository;
import com.example.waterdelivery.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResult login(String login, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login,
                        password
                )
        );

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtService.generateToken(authentication);

        return new AuthResult(
                user,
                token
        );
    }

    public boolean existsAnyUser() {
        return userRepository.existsBy();
    }

    public User registerUser(String login, String password) {
        var roles = Set.of(Role.ROLE_USER);
        return registerUser(login, password, roles);
    }

    public User registerUser(String login, String password, Set<Role> roles) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new UserAlredyExist("Login already in use");
        }


        User user = User.builder()
                .id(UUID.randomUUID())
                .login(login)
                .password(passwordEncoder.encode(password))
                .roles(new HashSet<>())
                .build();

        Set<UserRole> userRole = roles.stream()
                .map(role -> UserRole.builder()
                        .id(UUID.randomUUID())
                        .user(user)
                        .role(role)
                        .build()
                )
                .collect(Collectors.toSet());

        user.setRoles(userRole);
        return userRepository.save(user);
    }
}
