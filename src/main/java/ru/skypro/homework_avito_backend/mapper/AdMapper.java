package ru.skypro.homework_avito_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework_avito_backend.dto.AdDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateAdDto;
import ru.skypro.homework_avito_backend.dto.ExtendedAdDto;
import ru.skypro.homework_avito_backend.model.Ad;

/**
 * Маппер для конвертации сущности {@link Ad} в DTO и обратно.
 * Используется для преобразования данных между слоем базы данных и REST API.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdMapper {

    /**
     * Конвертирует сущность объявления в краткое DTO для списков.
     *
     * @param ad сущность объявления
     * @return краткое DTO объявления
     */
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "pk", source = "id")
    AdDto toAdDto(Ad ad);

    /**
     * Конвертирует сущность объявления в расширенное DTO с информацией об авторе.
     *
     * @param ad сущность объявления
     * @return расширенное DTO объявления с данными автора
     */
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "phone", source = "author.phone")
    ExtendedAdDto toExtendedAdDto(Ad ad);

    /**
     * Конвертирует DTO создания/обновления объявления в сущность.
     * Поля id, image, author и comments игнорируются и устанавливаются отдельно в сервисе.
     *
     * @param dto DTO с данными объявления
     * @return новая сущность объявления
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Ad toEntity(CreateOrUpdateAdDto dto);
}
