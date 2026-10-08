package ru.skypro.homework_avito_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.LoginDto;
import ru.skypro.homework_avito_backend.dto.NewPasswordDto;
import ru.skypro.homework_avito_backend.dto.RegisterDto;
import ru.skypro.homework_avito_backend.dto.UserDto;
import ru.skypro.homework_avito_backend.service.AuthService;
import ru.skypro.homework_avito_backend.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Управление профилем пользователя и авторизацией")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя")
    public ResponseEntity<Void> login(@RequestBody LoginDto login) {
        if (authService.login(login.getUsername(), login.getPassword())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<Void> register(@RequestBody RegisterDto register) {
        if (authService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/users/set_password")
    @Operation(summary = "Обновление пароля")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPassword,
                                            Authentication authentication) {
        if (userService.setPassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/users/me")
    @Operation(summary = "Получение информации об авторизованном пользователе")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @PatchMapping("/users/me")
    @Operation(summary = "Обновление информации авторизованного пользователя")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user,
                                              Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(user, authentication.getName()));
    }

    @PatchMapping(value = "/users/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    public ResponseEntity<Void> updateUserImage(@RequestPart("image") MultipartFile image,
                                                Authentication authentication) {
        userService.updateUserImage(image, authentication.getName());
        return ResponseEntity.ok().build();
    }
}


