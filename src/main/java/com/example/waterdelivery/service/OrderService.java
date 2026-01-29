package com.example.waterdelivery.service;

import com.example.waterdelivery.model.Basket;
import com.example.waterdelivery.model.Order;
import com.example.waterdelivery.model.OrderStatus;
import com.example.waterdelivery.repository.OrderRepository;
import com.example.waterdelivery.service.util.OrderTool;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final BasketService basketService;
    private final OrderTool orderTool;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrderFromBasket(UUID userId, String deliveryAddress, ZonedDateTime deliveryDateTime) {
        Basket basket = basketService.getBasket(userId);

        Order order = orderTool.createFromBasket(basket, deliveryAddress, deliveryDateTime);
        order.getItems().forEach(item -> item.setOrder(order));
        basketService.deleteBasket(userId);
        return orderRepository.save(order);
    }

    public Order getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found")); // TODO normal exception

        return order;
    }

    public Page<Order> getUserOrders(UUID userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable);
    }

    public Order updateOrder(UUID orderId, OrderStatus status) {
        var order = getOrderById(orderId);
        order.setOrderStatus(status);

        return orderRepository.save(order);
    }
}
