package com.example.waterdelivery.service;

import com.example.waterdelivery.exception.ResourceNotFoundException;
import com.example.waterdelivery.model.Basket;
import com.example.waterdelivery.model.BasketItem;
import com.example.waterdelivery.model.Product;
import com.example.waterdelivery.model.User;
import com.example.waterdelivery.repository.BasketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserService userService;
    private final ProductService productService;

    public Basket createBasket(UUID userId) {
        User user = userService.findById(userId);

        var check = basketRepository.findByUserId(userId);
        if (check.isPresent()) {
            return check.get();
        }

        Basket basket = Basket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .build();

        return basketRepository.save(basket);
    }

    public Basket addProductToBasket(UUID userId, UUID productId, Long quantity) {
        Basket basket = getBasket(userId);

        Product product = productService.getById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        basket.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + quantity),
                        () -> {
                            BasketItem newItem = BasketItem.builder()
                                    .id(UUID.randomUUID())
                                    .basket(basket)
                                    .product(product)
                                    .quantity(quantity)
                                    .build();
                            basket.getItems().add(newItem);
                        }
                );

        return basketRepository.save(basket);
    }

    public Basket removeProductFromBasket(UUID userId, UUID productId) {
        Basket basket = getBasket(userId);

        basket.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> basket.getItems().remove(item));

        return basketRepository.save(basket);
    }

    public Basket updateProductQuantity(UUID userId, UUID productId, Long quantity) {

        if (quantity <= 0) {
            return removeProductFromBasket(userId, productId);
        } else {
            Basket basket = getBasket(userId);

            basket.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .map(item -> {
                        item.setQuantity(quantity);
                        return item;
                    })
                    .orElseThrow(() -> new ResourceNotFoundException("There is no such product in basket"));
            return basketRepository.save(basket);
        }

    }

    public void deleteBasket(UUID userId) {
        Basket basket = getBasket(userId);

        basketRepository.delete(basket);
    }

    public Basket getBasket(UUID userId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Basket not found"));

        return basket;
    }

}
