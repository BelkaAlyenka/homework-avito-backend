package ru.skypro.homework_avito_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework_avito_backend.model.Comment;

import java.util.List;

/**
 * Репозиторий для работы с сущностями {@link Comment} (комментарии).
 * <p>
 * Наследует стандартные CRUD-методы от {@link JpaRepository}
 * и предоставляет кастомные методы поиска, генерируемые Spring Data
 * на основе именования методов.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * Находит все комментарии к объявлению с указанным идентификатором.
     * Используется для получения списка комментариев под конкретным объявлением.
     *
     * @param adId идентификатор объявления
     * @return список комментариев к объявлению
     */
    List<Comment> findAllByAdId(Integer adId);
}

