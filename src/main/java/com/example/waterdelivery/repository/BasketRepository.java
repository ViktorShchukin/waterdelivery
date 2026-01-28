package com.example.waterdelivery.repository;

import com.example.waterdelivery.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, UUID> {
    Optional<Basket> findByUserId(UUID userId);
}