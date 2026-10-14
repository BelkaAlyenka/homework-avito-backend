package ru.skypro.homework_avito_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * REST-контроллер для раздачи статических файлов изображений.
 * <p>
 * Предоставляет эндпоинты для получения:
 * <ul>
 *   <li>изображений объявлений ({@code GET /ads/image/{fileName}});</li>
 *   <li>аватаров пользователей ({@code GET /users/me/image/{fileName}}).</li>
 * </ul>
 * <p>
 * Эндпоинты доступны без аутентификации (добавлены в белый список в {@code WebSecurityConfig}).
 * Контроллер защищен от атак Path Traversal: проверяется, что итоговый путь находится
 * внутри базовой директории изображений.
 * <p>
 * MIME-тип определяется автоматически через {@link Files#probeContentType(Path)}.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Изображения", description = "Раздача статических файлов изображений с сервера")
public class ImageController {

    private final Path rootImagesDir;

    public ImageController(@Value("${app.images.dir}") String baseImagesDir) {
        this.rootImagesDir = Paths.get(baseImagesDir).toAbsolutePath().normalize();
    }

    @GetMapping(value = "/ads/image/{fileName}")
    @Operation(summary = "Получение картинки объявления")
    public ResponseEntity<Resource> getAdImage(@PathVariable String fileName) {
        return getImageResource(fileName, "ads");
    }

    @GetMapping(value = "/users/me/image/{fileName}")
    @Operation(summary = "Получение аватара пользователя")
    public ResponseEntity<Resource> getUserImage(@PathVariable String fileName) {
        return getImageResource(fileName, "users");
    }

    private ResponseEntity<Resource> getImageResource(String fileName, String folderPrefix) {
        Path targetDir = rootImagesDir.resolve(folderPrefix).normalize();
        Path filePath = targetDir.resolve(fileName).normalize();

        if (!filePath.startsWith(targetDir)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
        try {
            String mimeType = Files.probeContentType(filePath);
            if (mimeType != null) {
                contentType = MediaType.parseMediaType(mimeType);
            }
        } catch (IOException e) {
        }

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(resource);
    }
}

