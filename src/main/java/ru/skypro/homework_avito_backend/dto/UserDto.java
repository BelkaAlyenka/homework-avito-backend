package ru.skypro.homework_avito_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация о пользователе")
public class UserDto {

    @Schema(description = "id пользователя", example = "1")
    private Integer id;

    @Schema(description = "логин пользователя (email)", example = "user@gmail.com")
    private String email;

    @Schema(description = "имя пользователя", example = "Иван")
    private String firstName;

    @Schema(description = "фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "телефон пользователя", example = "+79991112233")
    private String phone;

    @Schema(description = "роль пользователя", example = "USER")
    private String role;

    @Schema(description = "ссылка на аватар пользователя", example = "/users/image/1")
    private String image;
}
