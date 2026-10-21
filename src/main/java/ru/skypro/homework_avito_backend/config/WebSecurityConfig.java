package ru.skypro.homework_avito_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурация безопасности приложения Spring Security.
 * <p>
 * Настраивает:
 * <ul>
 *   <li>Цепочку фильтров безопасности ({@link SecurityFilterChain}) с разграничением доступа по URL</li>
 *   <li>Кодировщик паролей ({@link PasswordEncoder}) на основе алгоритма BCrypt</li>
 *   <li>Менеджер аутентификации ({@link AuthenticationManager}) для обработки процессов входа</li>
 * </ul>
 * <p>
 * Публичные эндпоинты (Swagger, логин, регистрация, получение изображений) доступны без аутентификации.
 * Остальные эндпоинты требуют аутентификации через Basic Auth.
 * Метод-level безопасность включена через {@code @EnableMethodSecurity} для использования {@code @PreAuthorize}.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    /**
     * Список публичных URL-путей, доступных без аутентификации.
     * Включает ресурсы Swagger, эндпоинты логина/регистрации и пути для загрузки изображений.
     */
    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/login",
            "/register",
            "/ads/image/**",
            "/users/me/image/**"
    };

    /**
     * Настраивает цепочку фильтров безопасности.
     * <p>
     * Отключает CSRF-защиту (так как API использует Basic Auth, а не сессии).
     * Включает CORS с настройками по умолчанию.
     * Разграничивает доступ: публичные пути доступны всем, GET /ads — всем,
     * остальные /ads/** и /users/** — только аутентифицированным пользователям.
     *
     * @param http объект {@link HttpSecurity} для настройки безопасности
     * @return построенная цепочка фильтров {@link SecurityFilterChain}
     * @throws Exception если произошла ошибка при настройке безопасности
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.GET, "/ads").permitAll()
                        .requestMatchers("/ads/**", "/users/**").authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Предоставляет кодировщик паролей на основе алгоритма BCrypt.
     * Используется для безопасного хеширования паролей при регистрации и их проверки при аутентификации.
     *
     * @return экземпляр {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Предоставляет {@link AuthenticationManager} для выполнения процесса аутентификации.
     * Используется в {@code AuthService} для проверки учетных данных пользователя при логине.
     *
     * @param configuration объект конфигурации аутентификации Spring Security
     * @return экземпляр {@link AuthenticationManager}
     * @throws Exception если произошла ошибка при получении менеджера
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}


