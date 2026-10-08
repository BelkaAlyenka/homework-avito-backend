package ru.skypro.homework_avito_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework_avito_backend.dto.AdDto;
import ru.skypro.homework_avito_backend.dto.AdsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateAdDto;
import ru.skypro.homework_avito_backend.dto.ExtendedAdDto;
import ru.skypro.homework_avito_backend.service.AdService;

@RestController
@RequestMapping("/ads")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Объявления", description = "Управление объявлениями на площадке")
public class AdController {

    private final AdService adService;

    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление объявления")
    public ResponseEntity<AdDto> addAd(@RequestPart("properties") CreateOrUpdateAdDto properties,
                                       @RequestPart("image") MultipartFile image,
                                       Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adService.addAd(properties, image, authentication.getName()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об объявлении")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adService.getAd(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление объявления")
    @ApiResponse(responseCode = "204", description = "No Content")
    @PreAuthorize("@adSecurity.isAdOwnerOrAdmin(#id, authentication)")
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id,
                                         Authentication authentication) {
        adService.removeAd(id, authentication.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление информации об объявлении")
    @PreAuthorize("@adSecurity.isAdOwnerOrAdmin(#id, authentication)")
    public ResponseEntity<AdDto> updateAds(@PathVariable("id") Integer id,
                                           @RequestBody CreateOrUpdateAdDto properties,
                                           Authentication authentication) {
        return ResponseEntity.ok(adService.updateAd(id, properties, authentication.getName()));
    }

    @GetMapping("/me")
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    public ResponseEntity<AdsDto> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adService.getAdsMe(authentication.getName()));
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление картинки объявления")
    @PreAuthorize("@adSecurity.isAdOwnerOrAdmin(#id, authentication)")
    public ResponseEntity<String[]> updateImage(@PathVariable("id") Integer id,
                                                @RequestPart("image") MultipartFile image,
                                                Authentication authentication) {
        String newImagePath = adService.updateImage(id, image, authentication.getName());
        return ResponseEntity.ok(new String[]{newImagePath});
    }
}

