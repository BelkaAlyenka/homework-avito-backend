package ru.skypro.homework_avito_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework_avito_backend.dto.RegisterDto;
import ru.skypro.homework_avito_backend.dto.UserDto;
import ru.skypro.homework_avito_backend.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "email", source = "username")
    User toEntity(RegisterDto registerDto);
}
