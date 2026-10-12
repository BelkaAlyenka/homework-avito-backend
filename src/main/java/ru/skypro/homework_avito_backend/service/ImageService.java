package ru.skypro.homework_avito_backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile image, String folderPrefix);

    void deleteImage(String imageRelativePath);
}

