package ru.skypro.homework_avito_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация об объявлении")
public class AdDto {

    @Schema(description = "id автора объявления", example = "1")
    private Integer author;

    @Schema(description = "ссылка на картинку объявления", example = "/ads/image/1")
    private String image;

    @Schema(description = "id объявления", example = "1")
    private Integer pk;

    @Schema(description = "цена объявления", example = "1000")
    private Integer price;

    @Schema(description = "заголовок объявления", example = "Табуретка")
    private String title;
}

