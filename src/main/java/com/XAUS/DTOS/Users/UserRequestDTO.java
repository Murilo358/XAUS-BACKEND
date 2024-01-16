package com.XAUS.DTOS.Users;

import com.XAUS.Models.User.Enums.UserRole;

import java.util.Date;

public record UserRequestDTO(String name, String cpf, String email, Date birthDate, String password, UserRole role) {

}
