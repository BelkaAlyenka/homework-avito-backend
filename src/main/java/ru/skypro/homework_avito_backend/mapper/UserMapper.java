package ru.skypro.homework_avito_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework_avito_backend.dto.RegisterDto;
import ru.skypro.homework_avito_backend.dto.UserDto;
import ru.skypro.homework_avito_backend.model.User;

/**
 * Маппер для конвертации сущности {@link User} в DTO и обратно.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    /**
     * Конвертирует сущность пользователя в DTO для отображения в профиле.
     *
     * @param user сущность пользователя
     * @return DTO пользователя
     */
    UserDto toDto(User user);

    /**
     * Конвертирует DTO регистрации в сущность пользователя.
     * Поле username из DTO маппится в поле email сущности.
     * Поля id, image, ads и comments игнорируются и устанавливаются отдельно.
     *
     * @param registerDto DTO с данными для регистрации
     * @return новая сущность пользователя
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "email", source = "username")
    User toEntity(RegisterDto registerDto);
}
