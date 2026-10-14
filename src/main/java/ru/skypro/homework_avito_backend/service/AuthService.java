package ru.skypro.homework_avito_backend.service;

import ru.skypro.homework_avito_backend.dto.RegisterDto;

/**
 * Сервис для аутентификации и регистрации пользователей.
 * Отвечает за проверку учетных данных и создание новых аккаунтов.
 */
public interface AuthService {

    /**
     * Выполняет аутентификацию пользователя по логину и паролю.
     *
     * @param userName логин пользователя (email)
     * @param password пароль пользователя
     * @return {@code true}, если аутентификация успешна; {@code false} в противном случае
     */
    boolean login(String userName, String password);

    /**
     * Регистрирует нового пользователя в системе.
     * Пароль шифруется перед сохранением в базу данных.
     *
     * @param registerDto DTO с данными для регистрации (userName, password, firstName и др.)
     * @return {@code true}, если регистрация прошла успешно; {@code false}, если пользователь уже существует
     */
    boolean register(RegisterDto registerDto);
}

