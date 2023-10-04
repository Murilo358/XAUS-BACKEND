package com.XAUS.DTOS;


import java.lang.reflect.Array;
import java.util.List;

public record OrderRequestDTO(Long userId, Long clientId, List products) {



}
