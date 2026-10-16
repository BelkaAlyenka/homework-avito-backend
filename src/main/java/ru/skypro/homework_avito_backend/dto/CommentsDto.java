package ru.skypro.homework_avito_backend.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO для передачи списка комментариев с общим количеством.
 */
@Data
public class CommentsDto {
    private Integer count;
    private List<CommentDto> results;
}