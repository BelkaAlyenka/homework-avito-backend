package ru.skypro.homework_avito_backend.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.NewPasswordDto;
import ru.skypro.homework_avito_backend.dto.UserDto;

/**
 * Сервис для управления профилями пользователей.
 * Предоставляет методы для получения, обновления данных профиля, смены пароля и аватара.
 */
public interface UserService {

    /**
     * Изменяет пароль пользователя после проверки текущего пароля.
     *
     * @param newPasswordDto DTO с текущим и новым паролем
     * @param username       email пользователя
     * @return {@code true}, если пароль успешно изменен; {@code false}, если текущий пароль неверный
     * @throws java.util.NoSuchElementException если пользователь не найден
     */
    boolean setPassword(NewPasswordDto newPasswordDto, String username);

    /**
     * Получает информацию о профиле пользователя по его email.
     *
     * @param username email пользователя
     * @return DTO с данными профиля пользователя
     * @throws java.util.NoSuchElementException если пользователь не найден
     */
    UserDto getUser(String username);

    /**
     * Обновляет данные профиля пользователя (имя, фамилия, телефон).
     *
     * @param userDto  DTO с новыми данными профиля
     * @param username email пользователя
     * @return обновленный DTO профиля
     * @throws java.util.NoSuchElementException если пользователь не найден
     */
    UserDto updateUser(UserDto userDto, String username);

    /**
     * Обновляет аватар пользователя.
     * Старый аватар автоматически удаляется из файловой системы.
     *
     * @param image    новый файл аватара
     * @param username email пользователя
     * @throws java.util.NoSuchElementException если пользователь не найден
     */
    void updateUserImage(MultipartFile image, String username);
}
