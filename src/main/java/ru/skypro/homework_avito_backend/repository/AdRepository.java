package ru.skypro.homework_avito_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework_avito_backend.model.Ad;

import java.util.List;

/**
 * Репозиторий для работы с сущностями {@link Ad} (объявления).
 * <p>
 * Наследует стандартные CRUD-методы от {@link JpaRepository}
 * и предоставляет кастомные методы поиска, генерируемые Spring Data
 * на основе именования методов.
 */
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {

    /**
     * Находит все объявления, созданные пользователем с указанным email.
     * Используется для получения списка объявлений текущего авторизованного пользователя.
     *
     * @param email email автора объявлений
     * @return список объявлений автора
     */
    List<Ad> findAllByAuthorEmail(String email);
}

