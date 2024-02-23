package com.XAUS.DTOS.Products;

import com.XAUS.Models.Products.Product;

import java.io.Serializable;

public record ProductResponseDTO(Long id, String name, String description, Float price, Integer quantity) implements Serializable {


    public ProductResponseDTO(Product product) {
        this(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity());
    }


}
