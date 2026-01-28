package com.example.waterdelivery.controller.mapper;

import com.example.waterdelivery.config.AppMapperConfig;
import com.example.waterdelivery.controller.dto.ProductDto;
import com.example.waterdelivery.model.Product;
import org.mapstruct.Mapper;

@Mapper(config = AppMapperConfig.class)
public interface ProductMapper {

    Product fromDto(ProductDto productDto);

    ProductDto toDto(Product res);
}
