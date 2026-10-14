package ru.skypro.homework_avito_backend.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO для передачи краткой информации об объявлении в списках.
 */
@Data
public class AdsDto {
    private Integer count;
    private List<AdDto> results;
}
