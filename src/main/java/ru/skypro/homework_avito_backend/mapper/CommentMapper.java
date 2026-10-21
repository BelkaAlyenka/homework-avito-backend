package ru.skypro.homework_avito_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework_avito_backend.dto.CommentDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework_avito_backend.model.Comment;

/**
 * Маппер для конвертации сущности {@link Comment} в DTO и обратно.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    /**
     * Конвертирует сущность комментария в DTO с информацией об авторе.
     *
     * @param comment сущность комментария
     * @return DTO комментария
     */
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorImage", source = "author.image")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "pk", source = "id")
    CommentDto toCommentDto(Comment comment);

    /**
     * Конвертирует DTO создания/обновления комментария в сущность.
     * Поля id, createdAt, author и ad игнорируются и устанавливаются отдельно в сервисе.
     *
     * @param dto DTO с текстом комментария
     * @return новая сущность комментария
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ad", ignore = true)
    Comment toEntity(CreateOrUpdateCommentDto dto);
}