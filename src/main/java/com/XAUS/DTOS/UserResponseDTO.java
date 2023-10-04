package com.XAUS.DTOS;

import com.XAUS.Models.User;

import java.util.Date;

public record UserResponseDTO(Long id, String name, String cpf, String email, Date birthDate, String password) {


    public UserResponseDTO(User user) {
        this(user.getId(),
                user.getName(),
                user.getCpf(),
                user.getEmail(),
                user.getBirthDate(),
                user.getPassword());
    }


}
