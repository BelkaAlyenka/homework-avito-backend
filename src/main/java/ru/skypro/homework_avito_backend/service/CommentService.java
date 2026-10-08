package ru.skypro.homework_avito_backend.service;

import ru.skypro.homework_avito_backend.dto.CommentDto;
import ru.skypro.homework_avito_backend.dto.CommentsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateCommentDto;

public interface CommentService {

    CommentsDto getComments(Integer adId);

    CommentDto addComment(Integer adId, CreateOrUpdateCommentDto text, String username);

    void deleteComment(Integer adId, Integer commentId, String username);

    CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto text, String username);
}
