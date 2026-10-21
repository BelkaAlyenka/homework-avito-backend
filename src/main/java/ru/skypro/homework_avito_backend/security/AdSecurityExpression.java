package ru.skypro.homework_avito_backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework_avito_backend.model.Ad;
import ru.skypro.homework_avito_backend.model.Comment;
import ru.skypro.homework_avito_backend.model.User;
import ru.skypro.homework_avito_backend.repository.AdRepository;
import ru.skypro.homework_avito_backend.repository.CommentRepository;

import java.util.Objects;

/**
 * Компонент с кастомными выражениями безопасности для проверки прав доступа.
 * Используется в аннотациях {@code @PreAuthorize} контроллеров через SpEL-выражения
 * (например, {@code @adSecurity.isAdOwnerOrAdmin(#id, authentication)}).
 * <p>
 * Предоставляет методы для проверки, является ли текущий пользователь
 * автором сущности (объявления или комментария) или администратором.
 */
@Component("adSecurity")
@RequiredArgsConstructor
public class AdSecurityExpression {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;

    /**
     * Проверяет, имеет ли пользователь право на управление объявлением.
     * Доступ разрешен, если пользователь является администратором (роль ADMIN)
     * или автором объявления.
     *
     * @param adId           уникальный идентификатор объявления
     * @param authentication объект аутентификации текущего пользователя
     * @return {@code true}, если пользователь является автором объявления или администратором;
     * {@code false} в противном случае
     */
    @Transactional(readOnly = true)
    public boolean isAdOwnerOrAdmin(Integer adId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        return adRepository.findById(adId)
                .map(Ad::getAuthor)
                .map(User::getEmail)
                .map(email -> Objects.equals(email, authentication.getName()))
                .orElse(false);
    }

    /**
     * Проверяет, имеет ли пользователь право на управление комментарием.
     * Доступ разрешен, если пользователь является администратором (роль ADMIN)
     * или автором комментария.
     *
     * @param commentId      уникальный идентификатор комментария
     * @param authentication объект аутентификации текущего пользователя
     * @return {@code true}, если пользователь является автором комментария или администратором;
     * {@code false} в противном случае
     */
    @Transactional(readOnly = true)
    public boolean isCommentOwnerOrAdmin(Integer commentId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        return commentRepository.findById(commentId)
                .map(Comment::getAuthor)
                .map(User::getEmail)
                .map(email -> Objects.equals(email, authentication.getName()))
                .orElse(false);
    }
}

