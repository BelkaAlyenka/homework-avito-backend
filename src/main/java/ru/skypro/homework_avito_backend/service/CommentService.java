package ru.skypro.homework_avito_backend.service;

import ru.skypro.homework_avito_backend.dto.CommentDto;
import ru.skypro.homework_avito_backend.dto.CommentsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateCommentDto;

/**
 * Сервис для управления комментариями к объявлениям.
 * Предоставляет методы для создания, чтения, обновления и удаления комментариев.
 */
public interface CommentService {

    /**
     * Получает список всех комментариев к конкретному объявлению.
     *
     * @param adId уникальный идентификатор объявления
     * @return объект {@link CommentsDto}, содержащий количество и список комментариев
     */
    CommentsDto getComments(Integer adId);

    /**
     * Добавляет новый комментарий к объявлению от имени авторизованного пользователя.
     *
     * @param adId     уникальный идентификатор объявления
     * @param text     DTO с текстом комментария
     * @param username email автора комментария
     * @return созданный комментарий в формате {@link CommentDto}
     * @throws java.util.NoSuchElementException если объявление или пользователь не найдены
     */
    CommentDto addComment(Integer adId, CreateOrUpdateCommentDto text, String username);

    /**
     * Удаляет комментарий по его ID.
     * Доступно только автору комментария или администратору (проверяется на уровне контроллера).
     *
     * @param adId      уникальный идентификатор объявления (для контекста)
     * @param commentId уникальный идентификатор комментария
     * @param username  email пользователя, выполняющего удаление
     * @throws java.util.NoSuchElementException если комментарий не найден
     */
    void deleteComment(Integer adId, Integer commentId, String username);

    /**
     * Обновляет текст существующего комментария.
     * Доступно только автору комментария или администратору.
     *
     * @param adId      уникальный идентификатор объявления
     * @param commentId уникальный идентификатор комментария
     * @param text      DTO с новым текстом комментария
     * @param username  email пользователя, выполняющего обновление
     * @return обновленный комментарий в формате {@link CommentDto}
     * @throws java.util.NoSuchElementException если комментарий не найден
     */
    CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto text, String username);
}
