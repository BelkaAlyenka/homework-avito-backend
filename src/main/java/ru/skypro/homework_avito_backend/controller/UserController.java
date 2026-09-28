package ru.skypro.homework_avito_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Управление профилем пользователя и авторизацией")
public class UserController {

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя")
    public ResponseEntity<Void> login(@RequestBody LoginDto login) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<Void> register(@RequestBody RegisterDto register) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/users/set_password")
    @Operation(summary = "Обновление пароля")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPassword) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/me")
    @Operation(summary = "Получение информации об авторизованном пользователе")
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(new UserDto());
    }

    @PatchMapping("/users/me")
    @Operation(summary = "Обновление информации авторизованного пользователя")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(user);
    }

    @PatchMapping(value = "/users/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    public ResponseEntity<Void> updateUserImage(@RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok().build();
    }
}

