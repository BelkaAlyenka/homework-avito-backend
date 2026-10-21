package ru.skypro.homework_avito_backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.homework_avito_backend.dto.AdDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateAdDto;
import ru.skypro.homework_avito_backend.dto.ExtendedAdDto;
import ru.skypro.homework_avito_backend.mapper.AdMapper;
import ru.skypro.homework_avito_backend.model.Ad;
import ru.skypro.homework_avito_backend.model.User;
import ru.skypro.homework_avito_backend.repository.AdRepository;
import ru.skypro.homework_avito_backend.repository.UserRepository;
import ru.skypro.homework_avito_backend.service.ImageService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AdMapper adMapper;
    @Mock
    private ImageService imageService;

    @InjectMocks
    private AdServiceImpl adService;

    @Test
    void addAd_Success() {
        String username = "author@gmail.com";
        CreateOrUpdateAdDto properties = new CreateOrUpdateAdDto();
        properties.setTitle("Стол");
        properties.setPrice(500);

        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "table.jpg", "image/jpeg", "image-bytes".getBytes()
        );

        User author = new User();
        author.setEmail(username);

        Ad ad = new Ad();
        AdDto expectedDto = new AdDto();

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(author));
        when(adMapper.toEntity(properties)).thenReturn(ad);
        when(imageService.uploadImage(imageFile, "ads")).thenReturn("generated-uuid.jpg");
        when(adRepository.save(any(Ad.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(adMapper.toAdDto(any(Ad.class))).thenReturn(expectedDto);

        AdDto result = adService.addAd(properties, imageFile, username);

        assertNotNull(result);

        ArgumentCaptor<Ad> adCaptor = ArgumentCaptor.forClass(Ad.class);
        verify(adRepository, times(1)).save(adCaptor.capture());
        assertEquals("/ads/image/generated-uuid.jpg", adCaptor.getValue().getImage());
    }

    @Test
    void getAd_Success() {
        Integer adId = 1;
        Ad ad = new Ad();
        ExtendedAdDto expectedDto = new ExtendedAdDto();

        when(adRepository.findById(adId)).thenReturn(Optional.of(ad));
        when(adMapper.toExtendedAdDto(ad)).thenReturn(expectedDto);

        ExtendedAdDto result = adService.getAd(adId);

        assertNotNull(result);
        verify(adRepository, times(1)).findById(adId);
    }

    @Test
    void getAd_ThrowsException_WhenNotFound() {
        Integer adId = 999;
        when(adRepository.findById(adId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                adService.getAd(adId)
        );
        assertTrue(exception.getMessage().contains("Объявление не найдено"));
    }
}

