package com.XAUS.DTOS.Users;

public record UpdateUserDTO(String name, String email, String cpf, String role, Boolean enabled) {
}
