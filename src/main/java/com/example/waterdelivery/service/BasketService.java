package com.example.waterdelivery.service;

import com.example.waterdelivery.model.Basket;
import com.example.waterdelivery.model.BasketItem;
import com.example.waterdelivery.model.Product;
import com.example.waterdelivery.model.User;
import com.example.waterdelivery.repository.BasketRepository;
import com.example.waterdelivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    @Transactional
    public Basket createBasket(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")); // TODO craete user service

        if (basketRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("User already has a basket");
        }

        Basket basket = Basket.builder()
                .id(UUID.randomUUID())
                .user(user)
                .build();

        return basketRepository.save(basket);
    }

    @Transactional
    public Basket addProductToBasket(UUID userId, UUID productId, Long quantity) {
        Basket basket = getBasket(userId);

        Product product = productService.getById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

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

    @Transactional
    public Basket removeProductFromBasket(UUID userId, UUID productId) {
        Basket basket = getBasket(userId);

        basket.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> basket.getItems().remove(item));

        return basketRepository.save(basket);
    }

    @Transactional
    public Basket updateProductQuantity(UUID userId, UUID productId, Long quantity) {

        if (quantity <= 0) {
            return removeProductFromBasket(userId, productId);
        } else {
            Basket basket = getBasket(userId);

            basket.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .ifPresent(item -> item.setQuantity(quantity));
            return basketRepository.save(basket);
        }

    }

    @Transactional
    public void deleteBasket(UUID userId) {
        Basket basket = getBasket(userId);

        basketRepository.delete(basket);
    }

    @Transactional
    public Basket getBasket(UUID userId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Basket not found"));

        return basket;
    }

}
