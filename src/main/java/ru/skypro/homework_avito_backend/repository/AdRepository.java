package ru.skypro.homework_avito_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework_avito_backend.model.Ad;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {

    List<Ad> findAllByAuthorEmail(String email);
}

