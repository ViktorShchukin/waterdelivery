package com.example.waterdelivery.service;

import com.example.waterdelivery.exception.ResourceNotFoundException;
import com.example.waterdelivery.model.User;
import com.example.waterdelivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
