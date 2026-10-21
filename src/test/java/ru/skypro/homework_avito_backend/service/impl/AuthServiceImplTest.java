package ru.skypro.homework_avito_backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework_avito_backend.dto.RegisterDto;
import ru.skypro.homework_avito_backend.model.User;
import ru.skypro.homework_avito_backend.mapper.UserMapper;
import ru.skypro.homework_avito_backend.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_Success() {
        String username = "user@gmail.com";
        String password = "password";
        User user = new User();
        UserDetails userDetails = mock(UserDetails.class);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn("encoded_password");
        when(encoder.matches(password, "encoded_password")).thenReturn(true);

        boolean result = authService.login(username, password);

        assertTrue(result);
    }

    @Test
    void login_Fail_WhenUserNotFound() {
        when(userRepository.findByEmail("unknown@gmail.com")).thenReturn(Optional.empty());

        boolean result = authService.login("unknown@gmail.com", "password");

        assertFalse(result);
    }

    @Test
    void register_Success() {
        RegisterDto dto = new RegisterDto();
        dto.setUsername("newuser@gmail.com");
        dto.setPassword("raw_password");
        User user = new User();

        when(userRepository.findByEmail(dto.getUsername())).thenReturn(Optional.empty());
        when(encoder.encode(dto.getPassword())).thenReturn("encoded_password");
        when(userMapper.toEntity(dto)).thenReturn(user);

        boolean result = authService.register(dto);

        assertTrue(result);
        assertEquals("encoded_password", user.getPassword());
        assertEquals("USER", user.getRole());
        verify(userRepository, times(1)).save(user);
    }
}
