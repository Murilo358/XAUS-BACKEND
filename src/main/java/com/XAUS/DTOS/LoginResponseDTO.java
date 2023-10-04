package com.XAUS.DTOS;

import com.XAUS.Models.User;

import java.util.Collection;
import java.util.List;

public record LoginResponseDTO(String token, Collection Roles) {

}
