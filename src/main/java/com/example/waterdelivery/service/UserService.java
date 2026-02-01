package com.example.waterdelivery.service;

import com.example.waterdelivery.model.User;
import com.example.waterdelivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
