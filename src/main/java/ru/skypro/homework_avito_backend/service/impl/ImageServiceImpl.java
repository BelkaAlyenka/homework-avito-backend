package ru.skypro.homework_avito_backend.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${app.images.dir}")
    private String baseImagesDir;

    @Override
    public String uploadImage(MultipartFile image, String folderPrefix) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Файл картинки не может быть пустым");
        }

        try {
            Path uploadPath = Paths.get(baseImagesDir, folderPrefix).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String extension = getFileExtension(image.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID() + extension;
            Path targetFilePath = uploadPath.resolve(uniqueFileName).normalize();

            if (!targetFilePath.startsWith(uploadPath)) {
                throw new IllegalArgumentException("Некорректный путь к файлу!");
            }

            Files.write(targetFilePath, image.getBytes());

            return uniqueFileName;

        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить картинку на диск", e);
        }
    }

    private String getFileExtension(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return ".jpg";
        }
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return "";
        }
        return fileName.substring(lastIndexOfDot).toLowerCase();
    }

    @Override
    public void deleteImage(String imageRelativePath) {
        if (!org.springframework.util.StringUtils.hasText(imageRelativePath)) {
            return;
        }

        try {
            String folderPrefix = imageRelativePath.contains("/ads/") ? "ads" : "users";

            int lastSlashIndex = imageRelativePath.lastIndexOf("/");
            if (lastSlashIndex == -1) {
                return;
            }
            String fileName = imageRelativePath.substring(lastSlashIndex + 1);

            java.nio.file.Path filePath = java.nio.file.Paths.get(baseImagesDir, folderPrefix, fileName)
                    .toAbsolutePath().normalize();

            java.nio.file.Files.deleteIfExists(filePath);

        } catch (java.io.IOException e) {
            throw new RuntimeException("Не удалось удалить старую картинку с диска: " + imageRelativePath, e);
        }
    }
}

