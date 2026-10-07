package ru.skypro.homework_avito_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.NewPasswordDto;
import ru.skypro.homework_avito_backend.dto.UserDto;
import ru.skypro.homework_avito_backend.model.User;
import ru.skypro.homework_avito_backend.mapper.UserMapper;
import ru.skypro.homework_avito_backend.repository.UserRepository;
import ru.skypro.homework_avito_backend.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public boolean setPassword(NewPasswordDto newPasswordDto, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + username));

        if (!passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto getUser(String username) {
        return userRepository.findByEmail(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + username));
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + username));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void updateUserImage(MultipartFile image, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + username));

        String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        String imagePath = "/users/me/image/" + uniqueFileName;

        user.setImage(imagePath);
        userRepository.save(user);
    }
}


