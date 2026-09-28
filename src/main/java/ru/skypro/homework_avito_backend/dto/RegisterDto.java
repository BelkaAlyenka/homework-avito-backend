package ru.skypro.homework_avito_backend.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
}
