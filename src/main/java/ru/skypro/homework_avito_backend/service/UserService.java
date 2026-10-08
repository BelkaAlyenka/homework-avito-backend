package ru.skypro.homework_avito_backend.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.NewPasswordDto;
import ru.skypro.homework_avito_backend.dto.UserDto;

public interface UserService {

    boolean setPassword(NewPasswordDto newPasswordDto, String username);

    UserDto getUser(String username);

    UserDto updateUser(UserDto userDto, String username);

    void updateUserImage(MultipartFile image, String username);
}
