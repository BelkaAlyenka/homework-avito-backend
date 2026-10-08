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

@Component("adSecurity")
@RequiredArgsConstructor
public class AdSecurityExpression {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;

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

