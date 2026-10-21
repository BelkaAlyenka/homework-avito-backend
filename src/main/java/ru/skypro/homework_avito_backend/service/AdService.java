package ru.skypro.homework_avito_backend.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.AdDto;
import ru.skypro.homework_avito_backend.dto.AdsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateAdDto;
import ru.skypro.homework_avito_backend.dto.ExtendedAdDto;

import java.util.NoSuchElementException;

/**
 * Сервис для управления объявлениями.
 * Предоставляет методы для создания, чтения, обновления и удаления объявлений,
 * а также для работы с изображениями объявлений.
 */
public interface AdService {

    /**
     * Получает список всех объявлений.
     *
     * @return объект {@link AdsDto}, содержащий общее количество объявлений и их список
     */
    AdsDto getAllAds();

    /**
     * Создает новое объявление с изображением.
     *
     * @param properties данные для создания объявления (заголовок, цена, описание)
     * @param image      файл изображения объявления
     * @param username   email автора объявления (извлекается из контекста безопасности)
     * @return созданное объявление в формате {@link AdDto}
     * @throws IllegalArgumentException если пользователь с указанным username не найден
     */
    AdDto addAd(CreateOrUpdateAdDto properties, MultipartFile image, String username);

    /**
     * Получает расширенную информацию о конкретном объявлении по его ID.
     *
     * @param id уникальный идентификатор объявления
     * @return объект {@link ExtendedAdDto} с полной информацией об объявлении
     * @throws IllegalArgumentException если объявление с указанным ID не найдено
     */
    ExtendedAdDto getAd(Integer id);

    /**
     * Удаляет объявление по его ID.
     * Доступно только автору объявления или администратору (проверяется на уровне контроллера).
     *
     * @param id       уникальный идентификатор объявления
     * @param username email пользователя, выполняющего удаление
     * @throws IllegalArgumentException если объявление с указанным ID не найдено
     */
    void removeAd(Integer id, String username);

    /**
     * Обновляет информацию о существующем объявлении (заголовок, цена, описание).
     * Доступно только автору объявления или администратору.
     *
     * @param id         уникальный идентификатор объявления
     * @param properties новые данные объявления
     * @param username   email пользователя, выполняющего обновление
     * @return обновленное объявление в формате {@link AdDto}
     * @throws IllegalArgumentException если объявление с указанным ID не найдено
     */
    AdDto updateAd(Integer id, CreateOrUpdateAdDto properties, String username);

    /**
     * Получает список всех объявлений, созданных текущим авторизованным пользователем.
     *
     * @param username email авторизованного пользователя
     * @return объект {@link AdsDto}, содержащий количество и список объявлений пользователя
     */
    AdsDto getAdsMe(String username);

    /**
     * Обновляет изображение существующего объявления.
     * Старое изображение автоматически удаляется из файловой системы.
     * Доступно только автору объявления или администратору.
     *
     * @param id       уникальный идентификатор объявления
     * @param image    новый файл изображения
     * @param username email пользователя, выполняющего обновление
     * @return относительный путь к новому изображению
     * @throws NoSuchElementException если объявление с указанным ID не найдено
     */
    String updateImage(Integer id, MultipartFile image, String username);
}

