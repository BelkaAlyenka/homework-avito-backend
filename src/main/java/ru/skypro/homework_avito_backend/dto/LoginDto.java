package ru.skypro.homework_avito_backend.dto;

import lombok.Data;

/**
 * DTO для аутентификации пользователя (логин и пароль).
 */
@Data
public class LoginDto {
    private String username;
    private String password;
}