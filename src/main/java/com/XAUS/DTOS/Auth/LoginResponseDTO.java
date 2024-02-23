package com.XAUS.DTOS.Auth;


import java.util.List;

public record LoginResponseDTO(String token, List<String> Roles) {

}
