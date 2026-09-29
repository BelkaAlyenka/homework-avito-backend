package ru.skypro.homework_avito_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdsDto {
    private Integer count;
    private List<AdDto> results;
}
