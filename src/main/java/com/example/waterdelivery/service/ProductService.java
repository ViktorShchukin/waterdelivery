package com.example.waterdelivery.service;

import com.example.waterdelivery.model.Product;
import com.example.waterdelivery.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(String name) {
        Product new_product = Product.builder()
                .id(UUID.randomUUID())
                .name(name)
                .build();
        return productRepository.save(new_product);
    }

    public Optional<Product> getById(UUID uuid) {
        return productRepository.findById(uuid);
    }

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void delete(UUID uuid) {
        productRepository.deleteById(uuid);
    }
}
