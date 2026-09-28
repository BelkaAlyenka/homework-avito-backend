package ru.skypro.homework_avito_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Расширенная информация об объявлении")
public class ExtendedAdDto {

    @Schema(description = "id объявления", example = "1")
    private Integer pk;

    @Schema(description = "имя автора", example = "Иван")
    private String authorFirstName;

    @Schema(description = "фамилия автора", example = "Иванов")
    private String authorLastName;

    @Schema(description = "описание объявления", example = "Очень прочная табуретка")
    private String description;

    @Schema(description = "логин автора (email)", example = "user@gmail.com")
    private String email;

    @Schema(description = "ссылка на картинку объявления", example = "/ads/image/1")
    private String image;

    @Schema(description = "телефон автора", example = "+79961112233")
    private String phone;

    @Schema(description = "цена объявления", example = "1000")
    private Integer price;

    @Schema(description = "заголовок объявления", example = "Табуретка")
    private String title;
}
