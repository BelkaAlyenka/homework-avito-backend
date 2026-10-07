package ru.skypro.homework_avito_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.AdDto;
import ru.skypro.homework_avito_backend.dto.AdsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateAdDto;
import ru.skypro.homework_avito_backend.dto.ExtendedAdDto;
import ru.skypro.homework_avito_backend.mapper.AdMapper;
import ru.skypro.homework_avito_backend.model.Ad;
import ru.skypro.homework_avito_backend.model.User;
import ru.skypro.homework_avito_backend.repository.AdRepository;
import ru.skypro.homework_avito_backend.repository.UserRepository;
import ru.skypro.homework_avito_backend.service.AdService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;

    @Override
    public AdsDto getAllAds() {
        List<AdDto> adDtoList = adRepository.findAll().stream()
                .map(adMapper::toAdDto)
                .collect(Collectors.toList());

        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adDtoList.size());
        adsDto.setResults(adDtoList);
        return adsDto;
    }

    @Override
    @Transactional
    public AdDto addAd(CreateOrUpdateAdDto properties, MultipartFile image, String username) {
        User author = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + username));

        Ad ad = adMapper.toEntity(properties);
        ad.setAuthor(author);

        String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        ad.setImage("/ads/image/" + uniqueFileName);

        Ad savedAd = adRepository.save(ad);
        return adMapper.toAdDto(savedAd);
    }

    @Override
    public ExtendedAdDto getAd(Integer id) {
        return adRepository.findById(id)
                .map(adMapper::toExtendedAdDto)
                .orElseThrow(() -> new IllegalArgumentException("Объявление не найдено с id: " + id));
    }

    @Override
    @Transactional
    public void removeAd(Integer id, String username) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Объявление не найдено с id: " + id));

        adRepository.delete(ad);
    }

    @Override
    @Transactional
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto properties, String username) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Объявление не найдено с id: " + id));

        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());

        Ad updatedAd = adRepository.save(ad);
        return adMapper.toAdDto(updatedAd);
    }

    @Override
    public AdsDto getAdsMe(String username) {
        List<AdDto> myAdDtos = adRepository.findAllByAuthorEmail(username).stream()
                .map(adMapper::toAdDto)
                .collect(Collectors.toList());

        AdsDto adsDto = new AdsDto();
        adsDto.setCount(myAdDtos.size());
        adsDto.setResults(myAdDtos);
        return adsDto;
    }

    @Override
    @Transactional
    public String updateImage(Integer id, MultipartFile image, String username) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Объявление не найдено с id: " + id));

        String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        String newImagePath = "/ads/image/" + uniqueFileName;

        ad.setImage(newImagePath);
        adRepository.save(ad);

        return newImagePath;
    }
}
