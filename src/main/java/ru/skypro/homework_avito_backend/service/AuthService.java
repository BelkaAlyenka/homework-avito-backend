package ru.skypro.homework_avito_backend.service;

import ru.skypro.homework_avito_backend.dto.RegisterDto;

public interface AuthService {

    boolean login(String userName, String password);

    boolean register(RegisterDto registerDto);
}

