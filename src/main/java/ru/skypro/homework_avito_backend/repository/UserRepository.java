package ru.skypro.homework_avito_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework_avito_backend.model.User;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link User} (пользователи).
 * <p>
 * Наследует стандартные CRUD-методы от {@link JpaRepository}
 * и предоставляет кастомные методы поиска, генерируемые Spring Data
 * на основе именования методов.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Находит пользователя по его email (используется как username для аутентификации).
     * Используется в {@code CustomUserDetailsService} при загрузке данных пользователя
     * и в сервисах для проверки существования пользователя.
     *
     * @param email email пользователя
     * @return {@link Optional} с пользователем, если он найден; пустой {@link Optional}, если пользователь не существует
     */
    Optional<User> findByEmail(String email);
}
