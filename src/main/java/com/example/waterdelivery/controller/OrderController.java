package com.example.waterdelivery.controller;

import com.example.waterdelivery.controller.dto.CreateOrderRequest;
import com.example.waterdelivery.controller.dto.OrderDto;
import com.example.waterdelivery.controller.dto.UpdateOrderRequest;
import com.example.waterdelivery.controller.mapper.OrderMapper;
import com.example.waterdelivery.model.Order;
import com.example.waterdelivery.security.CustomUserDetails;
import com.example.waterdelivery.security.annotation.IsAdmin;
import com.example.waterdelivery.security.annotation.IsUser;
import com.example.waterdelivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@IsUser
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody CreateOrderRequest createOrderRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        Order order = orderService.createOrderFromBasket(
                userDetails.getUser().getId(),
                createOrderRequest.getDeliveryAddress(),
                createOrderRequest.getDeliveryDateTime()
        );
        return ResponseEntity.ok(orderMapper.toDto(order));
    }

    @GetMapping("/order")
    public ResponseEntity<Page<OrderDto>> getUserOrders(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault Pageable pageable
            ) {
        Page<OrderDto> orders = orderService.getUserOrders(userDetails.getUser().getId(), pageable)
                .map(orderMapper::toDto);
        return ResponseEntity.ok(orders);
    }

    @IsAdmin
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderMapper.toDto(order));
    }

    @IsAdmin
    @GetMapping("/{userId}/order")
    public ResponseEntity<Page<OrderDto>> getUserOrdersByUserId(
            @PathVariable UUID userId,
            @PageableDefault Pageable pageable
    ) {
        Page<OrderDto> orders = orderService.getUserOrders(userId, pageable)
                .map(orderMapper::toDto);
        return ResponseEntity.ok(orders);
    }

    @IsAdmin
    @PutMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable UUID orderId,
            @RequestBody UpdateOrderRequest updateDto) {
        Order order = orderService.updateOrder(orderId, updateDto.getStatus());
        return ResponseEntity.ok(orderMapper.toDto(order));
    }
}
