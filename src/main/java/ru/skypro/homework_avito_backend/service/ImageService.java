package ru.skypro.homework_avito_backend.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для работы с изображениями (аватарами пользователей и картинками объявлений).
 * Отвечает за безопасное сохранение файлов в файловой системе и их удаление.
 */
public interface ImageService {

    /**
     * Сохраняет изображение в файловой системе с уникальным именем.
     * Защищен от атак Path Traversal.
     *
     * @param image        файл изображения для сохранения
     * @param folderPrefix поддиректория для сохранения ("ads" или "users")
     * @return уникальное имя сохраненного файла (с расширением)
     * @throws IllegalArgumentException если файл пустой или путь некорректный
     * @throws RuntimeException         если не удалось сохранить файл на диск
     */
    String uploadImage(MultipartFile image, String folderPrefix);

    /**
     * Удаляет изображение из файловой системы по его относительному пути.
     *
     * @param imageRelativePath относительный путь к изображению (например, "/ads/image/uuid.jpg")
     */
    void deleteImage(String imageRelativePath);
}
