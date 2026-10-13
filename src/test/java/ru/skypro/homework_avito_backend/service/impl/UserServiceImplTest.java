package ru.skypro.homework_avito_backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework_avito_backend.dto.NewPasswordDto;
import ru.skypro.homework_avito_backend.dto.UserDto;
import ru.skypro.homework_avito_backend.mapper.UserMapper;
import ru.skypro.homework_avito_backend.model.User;
import ru.skypro.homework_avito_backend.repository.UserRepository;
import ru.skypro.homework_avito_backend.service.ImageService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ImageService imageService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void setPassword_Success() {
        String username = "user@gmail.com";
        NewPasswordDto dto = new NewPasswordDto();
        dto.setCurrentPassword("old_pass");
        dto.setNewPassword("new_pass");

        User user = new User();
        user.setPassword("encoded_old_pass");

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("old_pass", "encoded_old_pass")).thenReturn(true);
        when(passwordEncoder.encode("new_pass")).thenReturn("encoded_new_pass");

        boolean result = userService.setPassword(dto, username);

        assertTrue(result);
        assertEquals("encoded_new_pass", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void setPassword_Fail_WhenPasswordIncorrect() {
        String username = "user@gmail.com";
        NewPasswordDto dto = new NewPasswordDto();
        dto.setCurrentPassword("wrong_pass");

        User user = new User();
        user.setPassword("encoded_old_pass");

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong_pass", "encoded_old_pass")).thenReturn(false);

        boolean result = userService.setPassword(dto, username);

        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUser_Success() {
        String username = "user@gmail.com";
        User user = new User();
        UserDto expectedDto = new UserDto();

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(expectedDto);

        UserDto result = userService.getUser(username);

        assertNotNull(result);
    }

    @Test
    void updateUserImage_Success() {
        String username = "user@gmail.com";
        MockMultipartFile file = new MockMultipartFile("image", "me.jpg", "image/jpeg", new byte[]{1});

        User user = new User();
        user.setImage("/users/me/image/old-avatar.jpg");

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(imageService.uploadImage(file, "users")).thenReturn("new-avatar.jpg");

        userService.updateUserImage(file, username);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        assertEquals("/users/me/image/new-avatar.jpg", userCaptor.getValue().getImage());
        verify(imageService, times(1)).deleteImage("/users/me/image/old-avatar.jpg");
    }
}

