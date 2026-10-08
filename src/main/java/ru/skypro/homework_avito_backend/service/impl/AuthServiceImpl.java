package ru.skypro.homework_avito_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework_avito_backend.dto.RegisterDto;
import ru.skypro.homework_avito_backend.model.User;
import ru.skypro.homework_avito_backend.mapper.UserMapper;
import ru.skypro.homework_avito_backend.repository.UserRepository;
import ru.skypro.homework_avito_backend.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean login(String userName, String password) {
        if (userRepository.findByEmail(userName).isEmpty()) {
            return false;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    @Transactional
    public boolean register(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getUsername()).isPresent()) {
            return false;
        }

        String encodedPassword = encoder.encode(registerDto.getPassword());

        User user = userMapper.toEntity(registerDto);
        user.setPassword(encodedPassword);
        if (user.getRole() == null) {
            user.setRole("USER");
        }

        userRepository.save(user);
        return true;
    }
}

