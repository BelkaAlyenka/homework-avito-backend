package ru.skypro.homework_avito_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Данные для создания или обновления объявления")
public class CreateOrUpdateAdDto {

    @Schema(description = "заголовок объявления", example = "Табуретка из дуба")
    private String title;

    @Schema(description = "цена объявления", example = "2000")
    private Integer price;

    @Schema(description = "описание объявления", example = "Очень прочная, ручная работа")
    private String description;
}
