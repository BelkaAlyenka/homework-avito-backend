package ru.skypro.homework_avito_backend.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}