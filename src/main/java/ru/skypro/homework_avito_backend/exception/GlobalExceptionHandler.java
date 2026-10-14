package ru.skypro.homework_avito_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * Глобальный обработчик исключений для всего приложения.
 * <p>
 * Перехватывает исключения, возникающие в контроллерах и сервисах,
 * и преобразует их в стандартизированные HTTP-ответы с соответствующими кодами состояния.
 * <p>
 * Обеспечивает единообразие ответов об ошибках для клиентов API:
 * <ul>
 *   <li>{@link BadCredentialsException} → 401 Unauthorized (неверные учетные данные)</li>
 *   <li>{@link AccessDeniedException} → 403 Forbidden (недостаточно прав)</li>
 *   <li>{@link IllegalArgumentException} → 400 Bad Request (некорректные данные запроса)</li>
 *   <li>{@link NoSuchElementException} → 404 Not Found (сущность не найдена)</li>
 * </ul>
 *
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение неверных учетных данных при аутентификации.
     * Возвращает HTTP 401 Unauthorized.
     *
     * @param e исключение {@link BadCredentialsException}
     * @return пустой ответ со статусом 401
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Обрабатывает исключение недостатка прав доступа.
     * Возникает, когда пользователь пытается выполнить действие, на которое у него нет прав
     * (например, удалить чужое объявление).
     * Возвращает HTTP 403 Forbidden.
     *
     * @param e исключение {@link AccessDeniedException}
     * @return пустой ответ со статусом 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Void> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Обрабатывает исключение некорректных данных запроса.
     * Возникает, когда бизнес-логика обнаруживает проблему с входными данными
     * (например, пользователь с таким email уже существует).
     * Возвращает HTTP 400 Bad Request с сообщением об ошибке в теле ответа.
     *
     * @param e исключение {@link IllegalArgumentException}
     * @return ответ со статусом 400 и сообщением об ошибке
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Обрабатывает исключение отсутствия запрашиваемой сущности.
     * Возникает, когда в базе данных не найдено объявление, комментарий или пользователь
     * с указанным идентификатором.
     * Возвращает HTTP 404 Not Found с сообщением об ошибке в теле ответа.
     *
     * @param e исключение {@link NoSuchElementException}
     * @return ответ со статусом 404 и сообщением об ошибке
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}

