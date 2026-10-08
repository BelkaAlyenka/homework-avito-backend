package ru.skypro.homework_avito_backend.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.AdDto;
import ru.skypro.homework_avito_backend.dto.AdsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateAdDto;
import ru.skypro.homework_avito_backend.dto.ExtendedAdDto;

public interface AdService {

    AdsDto getAllAds();

    AdDto addAd(CreateOrUpdateAdDto properties, MultipartFile image, String username);

    ExtendedAdDto getAd(Integer id);

    void removeAd(Integer id, String username);

    AdDto updateAd(Integer id, CreateOrUpdateAdDto properties, String username);

    AdsDto getAdsMe(String username);

    String updateImage(Integer id, MultipartFile image, String username);
}

