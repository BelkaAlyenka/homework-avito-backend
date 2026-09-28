package ru.skypro.homework_avito_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommentsDto {
    private Integer count;
    private List<CommentDto> results;
}