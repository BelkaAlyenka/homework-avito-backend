package ru.skypro.homework_avito_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework_avito_backend.dto.AdDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateAdDto;
import ru.skypro.homework_avito_backend.dto.ExtendedAdDto;
import ru.skypro.homework_avito_backend.model.Ad;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdMapper {

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "pk", source = "id")
    AdDto toAdDto(Ad ad);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "phone", source = "author.phone")
    ExtendedAdDto toExtendedAdDto(Ad ad);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Ad toEntity(CreateOrUpdateAdDto dto);
}
