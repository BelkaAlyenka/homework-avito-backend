package ru.skypro.homework_avito_backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework_avito_backend.model.User;
import ru.skypro.homework_avito_backend.repository.UserRepository;

/**
 * Кастомная реализация {@link UserDetailsService} для Spring Security.
 * Загружает данные пользователя из базы данных по его email (username)
 * и оборачивает их в объект {@link SecurityUser}, реализующий {@link UserDetails}.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Загружает пользователя по email для процесса аутентификации.
     *
     * @param username email пользователя (используется как логин)
     * @return объект {@link UserDetails}, содержащий данные пользователя и его роли
     * @throws UsernameNotFoundException если пользователь с указанным email не найден в базе данных
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));

        return new SecurityUser(user);
    }
}

