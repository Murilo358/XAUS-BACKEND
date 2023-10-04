package com.XAUS.DTOS;
//Por ser um record ele ja faz os getters e os setters
public record ProductRequestDTO(String name, String description, Float price, Integer quantity) {


}
