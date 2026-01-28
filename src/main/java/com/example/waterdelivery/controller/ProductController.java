package com.example.waterdelivery.controller;

import com.example.waterdelivery.controller.dto.ProductDto;
import com.example.waterdelivery.controller.mapper.ProductMapper;
import com.example.waterdelivery.security.annotation.IsAdmin;
import com.example.waterdelivery.security.annotation.IsUser;
import com.example.waterdelivery.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@IsUser
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @IsAdmin
    @PostMapping()
    public ResponseEntity<ProductDto> create(
            @RequestBody ProductDto productDto
    ) {
        // TODO validation of dto fields
        var res = productService.create(productDto.getName());
        return ResponseEntity.ok(productMapper.toDto(res));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(
            @PathVariable UUID id
    ) {
        return productService.getById(id)
                .map(productMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAll(
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.getAll(pageable).map(productMapper::toDto)
        );
    }

    @IsAdmin
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(
            @PathVariable UUID id,
            @RequestBody ProductDto productDto
    ) {
        if (!id.equals(productDto.getId())) {
            throw new IllegalArgumentException("Id in request path and request body is not the same");
        }
        var res = productService.update(productMapper.fromDto(productDto));

        return ResponseEntity.ok(
                productMapper.toDto(res)
        );
    }

    @IsAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable UUID id
    ) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
