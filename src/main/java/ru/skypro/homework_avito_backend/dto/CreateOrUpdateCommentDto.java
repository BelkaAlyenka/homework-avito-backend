package ru.skypro.homework_avito_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Данные для создания/обновления комментария")
public class CreateOrUpdateCommentDto {
    @Schema(description = "текст комментария", example = "Нормальный товар, пользуюсь")
    private String text;
}
