package com.example.waterdelivery.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "basket_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"basket_id", "product_id"})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BasketItem {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long quantity;
}
