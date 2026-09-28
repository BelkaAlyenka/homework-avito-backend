package ru.skypro.homework_avito_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация о комментарии")
public class CommentDto {
    @Schema(description = "id автора комментария")
    private Integer author;
    @Schema(description = "ссылка на аватар автора")
    private String authorImage;
    @Schema(description = "имя автора")
    private String authorFirstName;
    @Schema(description = "дата и время создания комментария в миллисекундах")
    private Long createdAt;
    @Schema(description = "id комментария")
    private Integer pk;
    @Schema(description = "текст комментария")
    private String text;
}
