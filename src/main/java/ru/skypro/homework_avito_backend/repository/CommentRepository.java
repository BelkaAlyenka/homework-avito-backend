package ru.skypro.homework_avito_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework_avito_backend.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
