package com.XAUS.DTOS;

import com.XAUS.Models.Product;

public record ProductResponseDTO(Long id, String name, String description, Float price, Integer quantity) {


    public ProductResponseDTO(Product product) {
        this(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity());
    }


}
