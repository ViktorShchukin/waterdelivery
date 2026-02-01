package com.example.waterdelivery.controller;

import com.example.waterdelivery.controller.dto.BasketDto;
import com.example.waterdelivery.controller.mapper.BasketMapper;
import com.example.waterdelivery.security.CustomUserDetails;
import com.example.waterdelivery.security.annotation.IsUser;
import com.example.waterdelivery.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user/basket")
@RequiredArgsConstructor
@IsUser
public class BasketController {

    private final BasketService basketService;
    private final BasketMapper basketMapper;

    @PostMapping()
    public ResponseEntity<BasketDto> createBasket(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var basket = basketService.createBasket(userDetails.getUser().getId());
        return ResponseEntity.ok(basketMapper.toDto(basket));
    }

    @GetMapping
    public ResponseEntity<BasketDto> getBasket(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var basket = basketService.getBasket(userDetails.getUser().getId());
        return ResponseEntity.ok(basketMapper.toDto(basket));
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<BasketDto> addProduct(
            @PathVariable UUID productId,
            @RequestParam(defaultValue = "1") Long quantity,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var basket = basketService.addProductToBasket(userDetails.getUser().getId(), productId, quantity);
        return ResponseEntity.ok(basketMapper.toDto(basket));
    }


    @PutMapping("/update-product/{productId}")
    public ResponseEntity<BasketDto> updateProductQuantity(
            @PathVariable UUID productId,
            @RequestParam(required = true) Long quantity,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var basket = basketService.updateProductQuantity(userDetails.getUser().getId(), productId, quantity);
        return ResponseEntity.ok(basketMapper.toDto(basket));
    }

    @DeleteMapping("/remove-product/{productId}")
    public ResponseEntity<BasketDto> removeProductFromBasket(
            @PathVariable UUID productId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        var basket = basketService.removeProductFromBasket(userDetails.getUser().getId(), productId);
        return ResponseEntity.ok(basketMapper.toDto(basket));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBasket(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        basketService.deleteBasket(userDetails.getUser().getId());
        return ResponseEntity.noContent().build();
    }
}