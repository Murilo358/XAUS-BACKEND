package com.XAUS.DTOS.Auth;


import java.util.List;

public record ValidateTokenDTO(List<String> roles, String userName, Long userId) {
}
