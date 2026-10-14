package ru.skypro.homework_avito_backend.dto;

import lombok.Data;

/**
 * DTO для смены пароля пользователя (текущий и новый пароль).
 */
@Data
public class NewPasswordDto {
    private String currentPassword;
    private String newPassword;
}